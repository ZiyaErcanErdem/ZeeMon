package org.zee.app.zeemon.extensions.parser;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.zee.app.zeemon.domain.Action;
import org.zee.app.zeemon.domain.ActionExecution;
import org.zee.app.zeemon.domain.CheckScript;
import org.zee.app.zeemon.domain.Content;
import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.domain.Flow;
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.domain.ScriptParam;
import org.zee.app.zeemon.domain.enumeration.DataType;
import org.zee.app.zeemon.domain.enumeration.ItemFormat;
import org.zee.app.zeemon.domain.enumeration.ScriptType;
import org.zee.app.zeemon.extensions.execution.ActionScriptExecutionContext;
import org.zee.app.zeemon.extensions.execution.CheckScriptExecutionContext;
import org.zee.app.zeemon.extensions.execution.ExecutionEngine;
import org.zee.app.zeemon.repository.CheckScriptRepository;
import org.zee.app.zeemon.repository.ContentMapperRepository;
import org.zee.app.zeemon.repository.FieldMappingRepository;
import org.zee.app.zeemon.repository.ScriptParamRepository;

import net.sf.jsqlparser.JSQLParserException;

@Service
@Transactional
public class MapperService {

    private final Logger log = LoggerFactory.getLogger(MapperService.class);

    private final CheckScriptRepository checkScriptRepository;
    private final ContentMapperRepository contentMapperRepository;
    private final FieldMappingRepository fieldMappingRepository;
    private final ScriptParamRepository scriptParamRepository;

    private final ExecutionEngine executionEngine;

    public MapperService(
    		CheckScriptRepository checkScriptRepository, 
    		ContentMapperRepository contentMapperRepository,
    		FieldMappingRepository fieldMappingRepository,
    		ScriptParamRepository scriptParamRepository,
    		
    		ExecutionEngine executionEngine
    ) {
        this.checkScriptRepository = checkScriptRepository;
        this.contentMapperRepository = contentMapperRepository;
        this.fieldMappingRepository = fieldMappingRepository;
        this.scriptParamRepository = scriptParamRepository;        

        this.executionEngine = executionEngine;
    }
    
    public boolean executeSqlAction(Long actionId) {
        
     	ActionScriptExecutionContext ctx = this.executionEngine.prepareActionScriptExecutionContext(actionId);
     	
        if (null == ctx) {
        	return false;
        }
        
        Action action = ctx.getAction();
        
        ActionExecution actionExecution = this.executionEngine.newExecution(action);
        ctx.setActionExecution(actionExecution);

    	boolean rc = this.executionEngine.executeAction(ctx);
    	log.debug("ActionResult [Action.id:{}] : {}", actionId, rc);
    	return true;
    	
    }
    
    public boolean executeSqlTask(Long taskId) {
    	        
     	CheckScriptExecutionContext ctx = this.executionEngine.prepareCheckScriptExecutionContext(taskId);
     	
        if (null == ctx) {
        	return false;
        }
        
        Flow flow = ctx.getFlow();
        
        FlowExecution flowExecution = this.executionEngine.newExecution(flow);
        ctx.setFlowExecution(flowExecution);

    	boolean rc = this.executionEngine.executeScript(ctx);
    	if (rc) {
    		this.executionEngine.saveExecutionContent(ctx);
    		List<Content> contents = ctx.getContents();
    		if (null != contents) {
    			contents.forEach(c -> log.debug("TestResult [Task.id:{}] : {}", taskId, c));
    		}
    		
    		// this.contentRepository.saveAll(result);
    	}
    	return true;
    	
    }
    

    
    public Boolean generateSqlParamsForCheckScript(Long id) {
        log.debug("Request to generate SqlParamsForCheckScript CheckScript.id : {}", id);
        CheckScript checkScript = this.checkScriptRepository.findById(id).map(d -> d).orElse(null);
        
        if (null == checkScript) {
        	return false;
        }
        
        String source = checkScript.getScriptSource();
        
        List<String> paramNames;
		try {
			paramNames = SqlParserUtil.extractParameters(source);
		} catch (JSQLParserException e) {
			throw new RuntimeException(e);
		}
		
		if (null == paramNames || paramNames.isEmpty()) {
			return false;
		}

		
		List<ScriptParam> scriptParams = this.createParametersForSQLColumns(checkScript, paramNames);
		
		if (null == scriptParams || scriptParams.isEmpty()) {
			return false;
		}
        return true;
    }

    private List<ScriptParam> createParametersForSQLColumns(CheckScript checkScript, List<String> paramNames) {
    	if (null == checkScript) {
    		return null;
    	}
    	
		if (null == paramNames || paramNames.isEmpty()) {
			return null;
		}
		
		List<ScriptParam> scriptParams = paramNames.stream().map(pn -> {
			ScriptParam sc = this.newSqlDefaultScriptParam(pn);
			sc.setCheckScript(checkScript);
			return sc;
		}).collect(Collectors.toList());

		scriptParams = this.scriptParamRepository.saveAll(scriptParams);
    	
    	return scriptParams;
	}

	public ContentMapper generateSqlMapperForCheckScript(Long id) {
        log.debug("Request to generateSqlMapperForCheckScript CheckScript.id : {}", id);
        CheckScript checkScript = this.checkScriptRepository.findById(id).map(d -> d).orElse(null);
        
        if (null == checkScript) {
        	return null;
        }
        
        String source = checkScript.getScriptSource();
        
        List<String> columnNames;
		try {
			columnNames = SqlParserUtil.extractColumnNames(source);
		} catch (JSQLParserException e) {
			throw new RuntimeException(e);
		}
		
		if (null == columnNames || columnNames.isEmpty()) {
			return null;
		}
		
		ContentMapper contentMapper = this.createContentMapperForSQLScript(checkScript);
		
		if (null == contentMapper || null == contentMapper.getId()) {
			return null;
		}
		
		List<FieldMapping> fieldMappings = this.createFieldMappingForSQLColumns(contentMapper, columnNames);
		
		if (null !=  fieldMappings && !fieldMappings.isEmpty()) {
			checkScript.setContentMapper(contentMapper);
			checkScript = this.checkScriptRepository.save(checkScript);
		}

        return contentMapper;
    }
    
    private ContentMapper createContentMapperForSQLScript(CheckScript checkScript) {
    	if (null == checkScript) {
    		return null;
    	}
    	
    	String scriptName = checkScript.getScriptName();
    	ScriptType scriptType = checkScript.getScriptType();
    	
    	if (ScriptType.SQL_SCRIPT != scriptType) {
    		return null;
    	}

    	ContentMapper mapper = new ContentMapper();
    	mapper.setMapperName(scriptName + "ContentMapper");
    	mapper.setContainsHeader(false);
    	mapper.setFieldDelimiter("NONE");
    	mapper.setItemFormat(ItemFormat.SQL_RESULTSET);
    	
    	mapper = contentMapperRepository.save(mapper);
    	return mapper;
    }
    
    private List<FieldMapping> createFieldMappingForSQLColumns(ContentMapper contentMapper, List<String> columnNames) {
    	if (null == contentMapper) {
    		return null;
    	}
    	
		if (null == columnNames || columnNames.isEmpty()) {
			return null;
		}
		
		List<FieldMapping> fieldMappings = columnNames.stream().map(cn -> {
			FieldMapping fm = this.newSqlDefaultFieldMapping(cn);
			fm.setContentMapper(contentMapper);
			return fm;
		}).collect(Collectors.toList());

		int sourceIndex = 0;
		int txtIndex = 0;
		int numIndex = 0;
		int dateIndex = 0;
		int boolIndex = 0;
		for(FieldMapping fm : fieldMappings) {
			fm.setSourceIndex(sourceIndex);
			sourceIndex++;
			if (sourceIndex < 21) {
				txtIndex++;
				fm.setTargetColName("txt" + txtIndex);
			} else if (sourceIndex < 41) {
				numIndex++;
				fm.setTargetColName("num" + numIndex);
			} else if (sourceIndex < 51) {
				dateIndex++;
				fm.setTargetColName("date" + dateIndex);
			} else if (sourceIndex < 56) {
				boolIndex++;
				fm.setTargetColName("bool" + boolIndex);
			}
		}

    	fieldMappings = this.fieldMappingRepository.saveAll(fieldMappings);
    	
    	return fieldMappings;
    }
    
    private FieldMapping newSqlDefaultFieldMapping(String sourceColumnName) {
    	
    	sourceColumnName = StringUtils.deleteAny(sourceColumnName, "\"");
		FieldMapping fm = new FieldMapping();
		
		fm.setSourceName(sourceColumnName);
		
		fm.setSourceIndex(0);
					
		fm.setSourceStartIndex(null);
		fm.setSourceEndIndex(null);
		fm.setRequiredData(false);
		
		fm.setSourceDataType(DataType.STRING);
		fm.setSourceFormat(null);

		fm.setTargetName(sourceColumnName);
		fm.setTargetDataType(DataType.STRING);
		fm.setTransformation(null);
		
		System.out.println("Column => " + sourceColumnName);
		return fm;
    }
    

	private ScriptParam newSqlDefaultScriptParam(String paramName) {
		paramName = StringUtils.deleteAny(paramName, "\"");
    	ScriptParam sp = new ScriptParam();
    	
    	sp.setParamName(paramName);
    	sp.setParamDataType(DataType.STRING);
    	sp.setParamExpression(null);    	
    	sp.setParamValue(null);
		
				
		System.out.println("Param => " + paramName);
		return sp;
	}
	

}
