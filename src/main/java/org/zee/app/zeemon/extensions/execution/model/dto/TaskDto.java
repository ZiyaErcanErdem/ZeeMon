package org.zee.app.zeemon.extensions.execution.model.dto;
import java.time.Instant;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.zee.app.zeemon.domain.enumeration.TaskState;


public class TaskDto {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String taskName;

    @Size(max = 512)
    private String taskDescription;

    private Instant nextExecutionStartTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskState taskState;

    @NotNull
    private Long agentId;

    @NotNull
    private Long checkScriptId;

    private Long flowId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public TaskDto taskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public TaskDto taskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
        return this;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Instant getNextExecutionStartTime() {
        return nextExecutionStartTime;
    }

    public TaskDto nextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
        return this;
    }

    public void setNextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public TaskDto taskState(TaskState taskState) {
        this.taskState = taskState;
        return this;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }


    public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Long getCheckScriptId() {
		return checkScriptId;
	}

	public void setCheckScriptId(Long checkScriptId) {
		this.checkScriptId = checkScriptId;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskDto)) {
            return false;
        }
        return id != null && id.equals(((TaskDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            "checkScriptId=" + getAgentId() +
            "flowId=" + getFlowId() +
            "checkScriptId=" + getCheckScriptId() +
            ", taskName='" + getTaskName() + "'" +
            ", taskDescription='" + getTaskDescription() + "'" +
            ", nextExecutionStartTime='" + getNextExecutionStartTime() + "'" +
            ", taskState='" + getTaskState() + "'" +
            "}";
    }
}
