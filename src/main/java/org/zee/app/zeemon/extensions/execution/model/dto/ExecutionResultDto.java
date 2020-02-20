package org.zee.app.zeemon.extensions.execution.model.dto;

import java.util.ArrayList;
import java.util.List;

public class ExecutionResultDto {
	
	private AgentDto agent;
	
	private FlowDto flow;
	private FlowExecutionDto flowExecution;
	
	private TaskDto task;
	private TaskExecutionDto taskExecution;
	
	private List<FieldMappingDto> mappings;
	
	private List<ContentDto> content;
	
	public ExecutionResultDto() {
		this.content = new ArrayList<>();
		this.mappings = new ArrayList<>();
	}
	
	public AgentDto getAgent() {
		return agent;
	}


	public void setAgent(AgentDto agent) {
		this.agent = agent;
	}


	public FlowDto getFlow() {
		return flow;
	}

	public void setFlow(FlowDto flow) {
		this.flow = flow;
	}

	public FlowExecutionDto getFlowExecution() {
		return flowExecution;
	}

	public void setFlowExecution(FlowExecutionDto flowExecution) {
		this.flowExecution = flowExecution;
	}

	public TaskDto getTask() {
		return task;
	}

	public void setTask(TaskDto task) {
		this.task = task;
	}

	public TaskExecutionDto getTaskExecution() {
		return taskExecution;
	}

	public void setTaskExecution(TaskExecutionDto taskExecution) {
		this.taskExecution = taskExecution;
	}

	public List<FieldMappingDto> getMappings() {
		return mappings;
	}

	public void setMappings(List<FieldMappingDto> mappings) {
		this.mappings = mappings;
	}

	public List<ContentDto> getContent() {
		return content;
	}

	public void setContent(List<ContentDto> content) {
		this.content = content;
	}
	
	public void addContent(ContentDto item) {
		this.content.add(item);
	}
	
	public void addMapping(FieldMappingDto item) {
		this.mappings.add(item);
	}
	
	

}
