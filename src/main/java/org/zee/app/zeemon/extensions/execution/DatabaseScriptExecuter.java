package org.zee.app.zeemon.extensions.execution;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.zee.app.zeemon.domain.ActionParam;
import org.zee.app.zeemon.domain.ActionScript;
import org.zee.app.zeemon.domain.CheckScript;
import org.zee.app.zeemon.domain.Content;
import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.domain.EndpointProperty;
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.domain.ScriptParam;
import org.zee.app.zeemon.domain.enumeration.DataType;
import org.zee.app.zeemon.extensions.expression.ExpressionContext;
import org.zee.app.zeemon.extensions.expression.RuleContext;
import org.zee.app.zeemon.extensions.expression.RuleContextBuilder;
import org.zee.app.zeemon.extensions.expression.RuleEngine;
import org.zee.app.zeemon.extensions.expression.RuleOutput;
import org.zee.app.zeemon.extensions.parser.ContentSqlRowMapper;
import org.zee.app.zeemon.extensions.parser.MappingContext;

@Component
@Transactional
public class DatabaseScriptExecuter {
	
	private final Logger log = LoggerFactory.getLogger(DatabaseScriptExecuter.class);
	
	private final RuleEngine ruleEngine;

	private Map<Long, DataSource> endpointDataSources = new HashMap<>();
	
	public DatabaseScriptExecuter(RuleEngine ruleEngine) {
		this.ruleEngine = ruleEngine;
	}
	
	public boolean executeActionScript(ActionScriptExecutionContext ctx ) {

		DataSource dataSource = this.getDataSourceFor(ctx.getEndpoint(), ctx.getEndpointProperties());
		if(null == dataSource) {
			return false;
		}

		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		ActionScript actionScript = ctx.getActionScript();
		
		String sql = actionScript.getActionSource(); 
		String code = actionScript.getActionCode(); 
		
		MapSqlParameterSource params = this.prepareSqlParams(ctx);

		
		jdbcTemplate.execute(sql, params, new PreparedStatementCallback<Object>() {

			@Override
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.execute();
			}
			
		});
		
		// ContentSqlRowMapper rowMapper = ContentSqlRowMapper.newInstance(mappingContext, this.ruleEngine);
		// List<Content> contentList = jdbcTemplate.query(sql, params, rowMapper);
		// ctx.setContents(contentList);
		return true;
	}
	
	public boolean executeCheckScript(CheckScriptExecutionContext ctx ) {

		DataSource dataSource = this.getDataSourceFor(ctx.getEndpoint(), ctx.getEndpointProperties());
		if(null == dataSource) {
			return false;
		}
		
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		CheckScript checkScript = ctx.getCheckScript();
		ContentMapper contentMapper = ctx.getContentMapper();
		List<FieldMapping> fieldMappings = ctx.getFieldMappings();
		
		String sql = checkScript.getScriptSource(); 
		MappingContext mappingContext = new MappingContext(contentMapper, fieldMappings);
		
		MapSqlParameterSource params = this.prepareSqlParams(ctx);
		
		ContentSqlRowMapper rowMapper = ContentSqlRowMapper.newInstance(mappingContext, this.ruleEngine);
		List<Content> contentList = jdbcTemplate.query(sql, params, rowMapper);
		ctx.setContents(contentList);
		return true;
	}
	
	private DataSource getDataSourceFor(Endpoint endpoint, List<EndpointProperty> props) {
		DataSource ds = null;
		if (null == endpoint || null == props || props.isEmpty()) {
			return null;
		}

		
		ds = this.endpointDataSources.get(endpoint.getId());
		
		if (null != ds) {
			return ds;
		}
		
		ds = this.createDataSourceFor(endpoint, props);
		
		if (null == ds) {
			return null;
		}
		
		
		this.endpointDataSources.put(endpoint.getId(), ds);
		return ds;
	}
	
	private DataSource createDataSourceFor(Endpoint endpoint, List<EndpointProperty> props) {	
		String driverClassName = null;
		String jdbcUrl = null;
		String username = null;
		String password = null;
		
		if (null == props || props.isEmpty()) {
			return null;
		}
		
		for(EndpointProperty p : props) {
			if ("driverClassName".equals(p.getPropKey())) {
				driverClassName = p.getPropValue();
			} else if ("jdbcUrl".equals(p.getPropKey())) {
				jdbcUrl = p.getPropValue();
			} else if ("username".equals(p.getPropKey())) {
				username = p.getPropValue();
			} else if ("password".equals(p.getPropKey())) {
				password = p.getPropValue();
			}
		}
		
		if (null == driverClassName || null == jdbcUrl || null == username) {
			return null;
		}
		
		DataSource dataSource =DataSourceBuilder.create()
	        .driverClassName(driverClassName)
	        .url(jdbcUrl)
	        .username(username)
	        .password(null == password ? "" : password)
	        .build();

		return dataSource;
	}
	
	private MapSqlParameterSource prepareSqlParams(CheckScriptExecutionContext ctx) {
		
		MapSqlParameterSource source = new MapSqlParameterSource();
		List<ScriptParam> params = ctx.getScriptParams();
		
		params.forEach(p -> {
			String paramName = p.getParamName();
			String pureValue = p.getParamValue();
			Object value;
			try {
				value = this.getParamValue(p);
				source.addValue(paramName, value);
			} catch (Exception e) {
				log.error("Can not parse param:" + paramName + " with value: " + pureValue, e);
				source.addValue(paramName, pureValue);
			}			
		});
		
		return source;
	}
	
	private MapSqlParameterSource prepareSqlParams(ActionScriptExecutionContext ctx) {
		
		MapSqlParameterSource source = new MapSqlParameterSource();
		List<ActionParam> params = ctx.getActionParams();
		
		params.forEach(p -> {
			String paramName = p.getParamName();
			String pureValue = p.getParamValue();
			Object value;
			try {
				value = this.getParamValue(p);
				source.addValue(paramName, value);
			} catch (Exception e) {
				log.error("Can not parse param:" + paramName + " with value: " + pureValue, e);
				source.addValue(paramName, pureValue);
			}			
		});
		
		return source;
	}

	private Object getParamValue(ScriptParam scriptParam) throws Exception {
		if (null == scriptParam) {
			return null;
		}
		DataType dt = scriptParam.getParamDataType();
		String strValue = scriptParam.getParamValue();
		String paramExpression = scriptParam.getParamExpression();
		
		if (!StringUtils.isEmpty(paramExpression)) {
			return this.parseParamExpression(paramExpression, strValue, scriptParam);
		}
		
		if (DataType.STRING == dt) {
			return strValue;
		} else if (DataType.BOOLEAN == dt) {
			if (StringUtils.isEmpty(strValue)) {
				return Boolean.FALSE;
			}
			return "#true#yes#evet#1#yes#evet#".contains("#"+strValue.toLowerCase()+"#");
		} else if (DataType.NUMBER == dt) {
			return new BigDecimal(strValue);
		} else if (DataType.DATE == dt) {
			return DateUtils.parseDateStrictly(strValue, Locale.getDefault(), "dd.MM.yyyy", "yyyy.MM.dd", "dd/MM/yyyy", "yyyy/MM/dd" , "dd.MM.yyyy HH:mm:ss", "yyyy.MM.dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss");
		}
		return strValue;
	}
	
	private Object getParamValue(ActionParam axtionParam) throws Exception {
		if (null == axtionParam) {
			return null;
		}
		DataType dt = axtionParam.getParamDataType();
		String strValue = axtionParam.getParamValue();
		String paramExpression = axtionParam.getParamExpression();
		
		if (!StringUtils.isEmpty(paramExpression)) {
			return this.parseParamExpression(paramExpression, strValue, axtionParam);
		}
		
		if (DataType.STRING == dt) {
			return strValue;
		} else if (DataType.BOOLEAN == dt) {
			if (StringUtils.isEmpty(strValue)) {
				return Boolean.FALSE;
			}
			return "#true#yes#evet#1#yes#evet#".contains("#"+strValue.toLowerCase()+"#");
		} else if (DataType.NUMBER == dt) {
			return new BigDecimal(strValue);
		} else if (DataType.DATE == dt) {
			return DateUtils.parseDateStrictly(strValue, Locale.getDefault(), "dd.MM.yyyy", "yyyy.MM.dd", "dd/MM/yyyy", "yyyy/MM/dd" , "dd.MM.yyyy HH:mm:ss", "yyyy.MM.dd HH:mm:ss", "dd/MM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss");
		}
		return strValue;
	}
	
	private <T> Object parseParamExpression(String expression, String val, T ctx) {
		ExpressionContext<String, T> expressionContext = new ExpressionContext<String, T>(val, ctx);
		RuleContext ruleContext = RuleContextBuilder.instance(expressionContext).build();
		
		RuleOutput output = this.ruleEngine.parse(expression, ruleContext);
		return (null == output ? null : output.getValue());
	}
}
