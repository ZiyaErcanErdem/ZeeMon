package org.zee.app.zeemon.extensions.execution.model.dto;
import java.time.Instant;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.zee.app.zeemon.domain.enumeration.ExecutionStatus;


public class TaskExecutionDto{

    private Long id;

    @NotNull
    private Instant executionStartTime;

    @NotNull
    private Instant executionEndTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ExecutionStatus executionStatus;


    @NotNull
    private Long taskId;

    @NotNull
    private Long flowExecutionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExecutionStartTime() {
        return executionStartTime;
    }

    public TaskExecutionDto executionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
        return this;
    }

    public void setExecutionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public Instant getExecutionEndTime() {
        return executionEndTime;
    }

    public TaskExecutionDto executionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
        return this;
    }

    public void setExecutionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public TaskExecutionDto executionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
        return this;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getFlowExecutionId() {
		return flowExecutionId;
	}

	public void setFlowExecutionId(Long flowExecutionId) {
		this.flowExecutionId = flowExecutionId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskExecutionDto)) {
            return false;
        }
        return id != null && id.equals(((TaskExecutionDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TaskExecution{" +
            "id=" + getId() +
            "flowExecutionId=" + getFlowExecutionId() +
            "taskId=" + getTaskId() +
            ", executionStartTime='" + getExecutionStartTime() + "'" +
            ", executionEndTime='" + getExecutionEndTime() + "'" +
            ", executionStatus='" + getExecutionStatus() + "'" +
            "}";
    }
}
