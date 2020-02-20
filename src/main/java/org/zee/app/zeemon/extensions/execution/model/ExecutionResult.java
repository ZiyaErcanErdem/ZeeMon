package org.zee.app.zeemon.extensions.execution.model;

import java.util.ArrayList;
import java.util.List;

import org.zee.app.zeemon.domain.Agent;
import org.zee.app.zeemon.domain.Content;
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.domain.Flow;
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.domain.Task;
import org.zee.app.zeemon.domain.TaskExecution;

public class ExecutionResult {
	
	private Agent agent;
	
	private Flow flow;
	private FlowExecution flowExecution;
	
	private Task task;
	private TaskExecution taskExecution;
	
	private List<FieldMapping> mappings;
	
	private List<Content> content;
	
	public ExecutionResult() {
		this.content = new ArrayList<>();
		this.mappings = new ArrayList<>();
	}
	
	public Agent getAgent() {
		return agent;
	}


	public void setAgent(Agent agent) {
		this.agent = agent;
	}


	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public FlowExecution getFlowExecution() {
		return flowExecution;
	}

	public void setFlowExecution(FlowExecution flowExecution) {
		this.flowExecution = flowExecution;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskExecution getTaskExecution() {
		return taskExecution;
	}

	public void setTaskExecution(TaskExecution taskExecution) {
		this.taskExecution = taskExecution;
	}

	public List<FieldMapping> getMappings() {
		return mappings;
	}

	public void setMappings(List<FieldMapping> mappings) {
		this.mappings = mappings;
	}

	public List<Content> getContent() {
		return content;
	}

	public void setContent(List<Content> content) {
		this.content = content;
	}
	
	public void addContent(Content item) {
		this.content.add(item);
	}
	
	public void addMapping(FieldMapping item) {
		this.mappings.add(item);
	}
	
	

}
