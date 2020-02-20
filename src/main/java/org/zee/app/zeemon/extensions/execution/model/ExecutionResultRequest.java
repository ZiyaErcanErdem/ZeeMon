package org.zee.app.zeemon.extensions.execution.model;

public class ExecutionResultRequest {
	private Long taskExecutionId;
	private Boolean contentOnly;
	
	public ExecutionResultRequest() {
		this.contentOnly = false;
	}

	public Long getTaskExecutionId() {
		return taskExecutionId;
	}

	public void setTaskExecutionId(Long taskExecutionId) {
		this.taskExecutionId = taskExecutionId;
	}
		

	public Boolean getContentOnly() {
		return contentOnly;
	}

	public void setContentOnly(Boolean contentOnly) {
		this.contentOnly = contentOnly;
	}

	@Override
	public String toString() {
		return "ExecutionResultRequest [taskExecutionId=" + taskExecutionId + ", contentOnly=" + contentOnly + "]";
	}
	
}
