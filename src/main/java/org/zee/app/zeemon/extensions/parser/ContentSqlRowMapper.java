package org.zee.app.zeemon.extensions.parser;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.zee.app.zeemon.domain.Content;
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.extensions.expression.RuleContext;
import org.zee.app.zeemon.extensions.expression.RuleContextBuilder;
import org.zee.app.zeemon.extensions.expression.RuleEngine;
import org.zee.app.zeemon.extensions.expression.RuleOutput;

public class ContentSqlRowMapper implements RowMapper<Content>{

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected RuleEngine ruleEngine;
	
	private MappingContext mappingContext;

	@Nullable
	private Class<Content> mappedClass;

	private boolean checkFullyPopulated = false;

	private boolean primitivesDefaultedForNullValue = false;

	@Nullable
	private ConversionService conversionService = DefaultConversionService.getSharedInstance();

	@Nullable
	private Map<String, PropertyDescriptor> mappedFields;

	@Nullable
	private Set<String> mappedProperties;

	public ContentSqlRowMapper(RuleEngine ruleEngine) {
		this.ruleEngine = ruleEngine;
	}


	public ContentSqlRowMapper(MappingContext mappingContext, RuleEngine ruleEngine) {
		this.mappingContext = mappingContext;
		this.ruleEngine = ruleEngine;
		initialize(Content.class);
	}

	public ContentSqlRowMapper(MappingContext mappingContext, boolean checkFullyPopulated) {
		initialize(Content.class);
		this.checkFullyPopulated = checkFullyPopulated;
	}

	public void setMappedClass(Class<Content> mappedClass) {
		if (this.mappedClass == null) {
			initialize(mappedClass);
		}
		else {
			if (this.mappedClass != mappedClass) {
				throw new InvalidDataAccessApiUsageException("The mapped class can not be reassigned to map to " + mappedClass + " since it is already providing mapping for " + this.mappedClass);
			}
		}
	}

	@Nullable
	public final Class<Content> getMappedClass() {
		return this.mappedClass;
	}

	public void setCheckFullyPopulated(boolean checkFullyPopulated) {
		this.checkFullyPopulated = checkFullyPopulated;
	}

	public boolean isCheckFullyPopulated() {
		return this.checkFullyPopulated;
	}

	public void setPrimitivesDefaultedForNullValue(boolean primitivesDefaultedForNullValue) {
		this.primitivesDefaultedForNullValue = primitivesDefaultedForNullValue;
	}

	public boolean isPrimitivesDefaultedForNullValue() {
		return this.primitivesDefaultedForNullValue;
	}

	public void setConversionService(@Nullable ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	@Nullable
	public ConversionService getConversionService() {
		return this.conversionService;
	}

	protected void initialize(Class<Content> mappedClass) {
		this.mappedClass = mappedClass;
		this.mappedFields = new HashMap<>();
		this.mappedProperties = new HashSet<>();
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
		for (PropertyDescriptor pd : pds) {
			if (pd.getWriteMethod() != null) {
				
				String pdName = pd.getName();
				String lowerPdName = lowerCaseName(pdName);
				String sourceColName = getSourceColName(pdName);
				String lowerSourceColName = StringUtils.isEmpty(sourceColName) ? null : lowerCaseName(sourceColName);
				
				if (!StringUtils.isEmpty(sourceColName)) {
					this.mappedFields.put(sourceColName, pd);
					if (!sourceColName.equals(lowerSourceColName)) {
						this.mappedFields.put(lowerSourceColName, pd);
					}
				}
				if (!lowerPdName.equals(lowerSourceColName)) {
					this.mappedFields.put(lowerPdName, pd);
				}

				String underscoredName = underscoreName(pd.getName());
				if (!lowerPdName.equals(underscoredName)) {
					this.mappedFields.put(underscoredName, pd);
				}
				this.mappedProperties.add(pdName);
			}
		}
	}
	
	private String getSourceColName(String targetColName) {
		if (null == this.mappingContext) {
			return null;
		}
		FieldMapping fm = this.mappingContext.getFieldMappingWithTargetColName(targetColName);
		if (null == fm || StringUtils.isEmpty(fm.getSourceName())) {
			return null;
		}
		String sourceColName = fm.getSourceName();
		return StringUtils.trimAllWhitespace(sourceColName);
	}
	
	private FieldMapping getFieldMapping(String targetColName) {
		if (null == this.mappingContext) {
			return null;
		}
		FieldMapping fm = this.mappingContext.getFieldMappingWithTargetColName(targetColName);
		return fm;
	}

	protected String underscoreName(String name) {
		if (!StringUtils.hasLength(name)) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		result.append(lowerCaseName(name.substring(0, 1)));
		for (int i = 1; i < name.length(); i++) {
			String s = name.substring(i, i + 1);
			String slc = lowerCaseName(s);
			if (!s.equals(slc)) {
				result.append("_").append(slc);
			}
			else {
				result.append(s);
			}
		}
		return result.toString();
	}

	protected String lowerCaseName(String name) {
		return name.toLowerCase(Locale.US);
	}

	@Override
	public Content mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Assert.state(this.mappedClass != null, "Mapped class was not specified");
		Content mappedObject = BeanUtils.instantiateClass(this.mappedClass);
		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
		initBeanWrapper(bw);

		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Set<String> populatedProperties = (isCheckFullyPopulated() ? new HashSet<>() : null);
		
		int sourceIndex = rowNumber + 1;
		bw.setPropertyValue("sourceIndex", sourceIndex);

		for (int index = 1; index <= columnCount; index++) {
			String column = JdbcUtils.lookupColumnName(rsmd, index);
			String field = lowerCaseName(StringUtils.delete(column, " "));
			PropertyDescriptor pd = (this.mappedFields != null ? this.mappedFields.get(field) : null);
			if (pd != null) {
				try {
					Object value = getColumnValue(rs, index, pd);
					if (rowNumber == 0 && logger.isDebugEnabled()) {
						logger.debug("Mapping column '" + column + "' to property '" + pd.getName() + "' of type '" + ClassUtils.getQualifiedName(pd.getPropertyType()) + "'");
					}
					try {
						bw.setPropertyValue(pd.getName(), value);
					}
					catch (TypeMismatchException ex) {
						if (value == null && this.primitivesDefaultedForNullValue) {
							if (logger.isDebugEnabled()) {
								logger.debug("Intercepted TypeMismatchException for row " + rowNumber +
										" and column '" + column + "' with null value when setting property '" +
										pd.getName() + "' of type '" +
										ClassUtils.getQualifiedName(pd.getPropertyType()) +
										"' on object: " + mappedObject, ex);
							}
						}
						else {
							throw ex;
						}
					}
					if (populatedProperties != null) {
						populatedProperties.add(pd.getName());
					}
				}
				catch (NotWritablePropertyException ex) {
					throw new DataRetrievalFailureException(
							"Unable to map column '" + column + "' to property '" + pd.getName() + "'", ex);
				}
			}
			else {
				// No PropertyDescriptor found
				if (rowNumber == 0 && logger.isDebugEnabled()) {
					logger.debug("No property found for column '" + column + "' mapped to field '" + field + "'");
				}
			}
		}

		if (populatedProperties != null && !populatedProperties.equals(this.mappedProperties)) {
			throw new InvalidDataAccessApiUsageException("Given ResultSet does not contain all fields " +
					"necessary to populate object of class [" + this.mappedClass.getName() + "]: " +
					this.mappedProperties);
		}

		return mappedObject;
	}

	protected void initBeanWrapper(BeanWrapper bw) {
		ConversionService cs = getConversionService();
		if (cs != null) {
			bw.setConversionService(cs);
		}
	}

	@Nullable
	protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
		
		FieldMapping fm = getFieldMapping(pd.getName());
		if (null == fm || StringUtils.isEmpty(fm.getTransformation())) {
			return JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());
		}
		String expression = fm.getTransformation();
		Object dbValue = JdbcUtils.getResultSetValue(rs, index);
		return this.parseFieldExpression(expression, dbValue, rs, index, fm);	
	}
	
	private Object parseFieldExpression(String expression, Object val, ResultSet rs, int index, FieldMapping fm) {
		SqlFieldMappingExpressionContext expressionContext = new SqlFieldMappingExpressionContext(val, rs, index, fm);
		RuleContext ruleContext = RuleContextBuilder.instance(expressionContext).build();
		
		RuleOutput output = this.ruleEngine.parse(expression, ruleContext);
		return (null == output ? null : output.getValue());
	}

	public static ContentSqlRowMapper newInstance( MappingContext mappingContext, RuleEngine ruleEngine) {
		return new ContentSqlRowMapper(mappingContext, ruleEngine);
	}
	
	
}
