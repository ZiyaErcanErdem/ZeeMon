package org.zee.app.zeemon.extensions.execution.model.dto;
import java.time.Instant;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.zee.app.zeemon.domain.enumeration.ExecutionStatus;


public class FlowExecutionDto {

    private Long id;

    @NotNull
    private Instant executionStartTime;

    @NotNull
    private Instant executionEndTime;

    @Min(value = 0)
    private Integer totalTaskCount;

    @Min(value = 0)
    private Integer runningTaskCount;

    @Min(value = 0)
    private Integer errorTaskCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ExecutionStatus executionStatus;

    @NotNull
    private Long flowId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExecutionStartTime() {
        return executionStartTime;
    }

    public FlowExecutionDto executionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
        return this;
    }

    public void setExecutionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public Instant getExecutionEndTime() {
        return executionEndTime;
    }

    public FlowExecutionDto executionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
        return this;
    }

    public void setExecutionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
    }

    public Integer getTotalTaskCount() {
        return totalTaskCount;
    }

    public FlowExecutionDto totalTaskCount(Integer totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
        return this;
    }

    public void setTotalTaskCount(Integer totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
    }

    public Integer getRunningTaskCount() {
        return runningTaskCount;
    }

    public FlowExecutionDto runningTaskCount(Integer runningTaskCount) {
        this.runningTaskCount = runningTaskCount;
        return this;
    }

    public void setRunningTaskCount(Integer runningTaskCount) {
        this.runningTaskCount = runningTaskCount;
    }

    public Integer getErrorTaskCount() {
        return errorTaskCount;
    }

    public FlowExecutionDto errorTaskCount(Integer errorTaskCount) {
        this.errorTaskCount = errorTaskCount;
        return this;
    }

    public void setErrorTaskCount(Integer errorTaskCount) {
        this.errorTaskCount = errorTaskCount;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public FlowExecutionDto executionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
        return this;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
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
        if (!(o instanceof FlowExecutionDto)) {
            return false;
        }
        return id != null && id.equals(((FlowExecutionDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FlowExecution{" +
            "flowId=" + getFlowId() +
            "id=" + getId() +
            ", executionStartTime='" + getExecutionStartTime() + "'" +
            ", executionEndTime='" + getExecutionEndTime() + "'" +
            ", totalTaskCount=" + getTotalTaskCount() +
            ", runningTaskCount=" + getRunningTaskCount() +
            ", errorTaskCount=" + getErrorTaskCount() +
            ", executionStatus='" + getExecutionStatus() + "'" +
            "}";
    }
}
