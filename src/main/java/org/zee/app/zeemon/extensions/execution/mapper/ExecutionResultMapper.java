package org.zee.app.zeemon.extensions.execution.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.zee.app.zeemon.domain.Agent;
import org.zee.app.zeemon.domain.Content;
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.domain.Flow;
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.domain.Task;
import org.zee.app.zeemon.domain.TaskExecution;
import org.zee.app.zeemon.extensions.execution.model.ExecutionResult;
import org.zee.app.zeemon.extensions.execution.model.dto.AgentDto;
import org.zee.app.zeemon.extensions.execution.model.dto.ContentDto;
import org.zee.app.zeemon.extensions.execution.model.dto.ExecutionResultDto;
import org.zee.app.zeemon.extensions.execution.model.dto.FieldMappingDto;
import org.zee.app.zeemon.extensions.execution.model.dto.FlowDto;
import org.zee.app.zeemon.extensions.execution.model.dto.FlowExecutionDto;
import org.zee.app.zeemon.extensions.execution.model.dto.TaskDto;
import org.zee.app.zeemon.extensions.execution.model.dto.TaskExecutionDto;

@Mapper
public interface ExecutionResultMapper {

	ExecutionResultMapper MAPPER = Mappers.getMapper( ExecutionResultMapper.class );
	
	
	AgentDto toAgentDto(Agent source); 
	
	@Mapping(source = "eventTrigger.id", target = "eventTriggerId")
	FlowDto toFlowDto(Flow source); 
	
	@Mapping(source = "agent.id", target = "agentId")
	@Mapping(source = "checkScript.id", target = "checkScriptId")
	@Mapping(source = "flow.id", target = "flowId")
	TaskDto toTaskDto(Task source); 
	
	@Mapping(source = "flow.id", target = "flowId")
	FlowExecutionDto toFlowExecutionDto(FlowExecution source); 
	
	@Mapping(source = "task.id", target = "taskId")
	@Mapping(source = "flowExecution.id", target = "flowExecutionId")
	TaskExecutionDto toTaskExecutionDto(TaskExecution source); 
	
	@Mapping(source = "contentMapper.id", target = "contentMapperId")
	FieldMappingDto toFieldMappingDto(FieldMapping source); 
	
	@Mapping(source = "flow.id", target = "flowId")
	@Mapping(source = "task.id", target = "taskId")
	@Mapping(source = "flowExecution.id", target = "flowExecutionId")
	@Mapping(source = "taskExecution.id", target = "taskExecutionId")
	@Mapping(source = "checkScript.id", target = "checkScriptId")
	ContentDto toContentDto(Content source); 
	
	ExecutionResultDto toExecutionResultDto(ExecutionResult source);
	
	// @InheritInverseConfiguration
	// ExecutionResult fromExecutionResultDto(ExecutionResultDto source);
    
}
