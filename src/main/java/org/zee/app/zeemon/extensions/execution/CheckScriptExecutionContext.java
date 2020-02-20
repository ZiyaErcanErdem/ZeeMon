package org.zee.app.zeemon.extensions.execution;

import java.util.List;

import org.zee.app.zeemon.domain.Agent;
import org.zee.app.zeemon.domain.CheckScript;
import org.zee.app.zeemon.domain.Content;
import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.domain.EndpointProperty;
import org.zee.app.zeemon.domain.EventTrigger;
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.domain.Flow;
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.domain.ScriptParam;
import org.zee.app.zeemon.domain.Task;
import org.zee.app.zeemon.domain.TaskExecution;

public class CheckScriptExecutionContext {
	
	private Flow flow;
	private Task task;
	private EventTrigger eventTrigger;
	private Agent agent;
	private Endpoint endpoint;
	private CheckScript checkScript;
	private ContentMapper contentMapper;
	private List<FieldMapping> fieldMappings;
	private List<ScriptParam> scriptParams;
	private List<EndpointProperty> endpointProperties;
	
	private FlowExecution flowExecution;
	private TaskExecution taskExecution;
	
	private List<Content> contents;
	
	public CheckScriptExecutionContext() {
		
	}
	
	public CheckScript getCheckScript() {
		return checkScript;
	}
	public void setCheckScript(CheckScript checkScript) {
		this.checkScript = checkScript;
	}
	public ContentMapper getContentMapper() {
		return contentMapper;
	}
	public void setContentMapper(ContentMapper contentMapper) {
		this.contentMapper = contentMapper;
	}
	public List<FieldMapping> getFieldMappings() {
		return fieldMappings;
	}
	public void setFieldMappings(List<FieldMapping> fieldMappings) {
		this.fieldMappings = fieldMappings;
	}
	public List<ScriptParam> getScriptParams() {
		return scriptParams;
	}
	public void setScriptParams(List<ScriptParam> scriptParams) {
		this.scriptParams = scriptParams;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public EventTrigger getEventTrigger() {
		return eventTrigger;
	}

	public void setEventTrigger(EventTrigger eventTrigger) {
		this.eventTrigger = eventTrigger;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	public List<EndpointProperty> getEndpointProperties() {
		return endpointProperties;
	}

	public void setEndpointProperties(List<EndpointProperty> endpointProperties) {
		this.endpointProperties = endpointProperties;
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public FlowExecution getFlowExecution() {
		return flowExecution;
	}

	public void setFlowExecution(FlowExecution flowExecution) {
		this.flowExecution = flowExecution;
	}

	public TaskExecution getTaskExecution() {
		return taskExecution;
	}

	public void setTaskExecution(TaskExecution taskExecution) {
		this.taskExecution = taskExecution;
	}

}
