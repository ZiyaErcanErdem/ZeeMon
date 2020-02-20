package org.zee.app.zeemon.extensions.execution;

import java.util.List;

import org.zee.app.zeemon.domain.Action;
import org.zee.app.zeemon.domain.ActionExecution;
import org.zee.app.zeemon.domain.ActionParam;
import org.zee.app.zeemon.domain.ActionScript;
import org.zee.app.zeemon.domain.Agent;
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.domain.EndpointProperty;

public class ActionScriptExecutionContext {
	
	private Action action;
	private Agent agent;
	private Endpoint endpoint;
	private ActionScript actionScript;
	private List<ActionParam> actionParams;
	private List<EndpointProperty> endpointProperties;

	private ActionExecution actionExecution;
	
	public ActionScriptExecutionContext() {
		
	}
	
	public ActionScript getActionScript() {
		return actionScript;
	}
	public void setActionScript(ActionScript actionScript) {
		this.actionScript = actionScript;
	}

	public List<ActionParam> getActionParams() {
		return actionParams;
	}
	public void setActionParams(List<ActionParam> actionParams) {
		this.actionParams = actionParams;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
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

	public ActionExecution getActionExecution() {
		return actionExecution;
	}

	public void setActionExecution(ActionExecution actionExecution) {
		this.actionExecution = actionExecution;
	}

}
