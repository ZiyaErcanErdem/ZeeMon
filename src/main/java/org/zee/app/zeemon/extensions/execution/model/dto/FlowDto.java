package org.zee.app.zeemon.extensions.execution.model.dto;
import java.time.Instant;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.zee.app.zeemon.domain.enumeration.FlowState;


public class FlowDto {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String flowName;

    @Size(max = 512)
    private String flowDescription;

    private Instant nextExecutionStartTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FlowState flowState;

    private Long eventTriggerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlowName() {
        return flowName;
    }

    public FlowDto flowName(String flowName) {
        this.flowName = flowName;
        return this;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowDescription() {
        return flowDescription;
    }

    public FlowDto flowDescription(String flowDescription) {
        this.flowDescription = flowDescription;
        return this;
    }

    public void setFlowDescription(String flowDescription) {
        this.flowDescription = flowDescription;
    }

    public Instant getNextExecutionStartTime() {
        return nextExecutionStartTime;
    }

    public FlowDto nextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
        return this;
    }

    public void setNextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
    }

    public FlowState getFlowState() {
        return flowState;
    }

    public FlowDto flowState(FlowState flowState) {
        this.flowState = flowState;
        return this;
    }

    public void setFlowState(FlowState flowState) {
        this.flowState = flowState;
    }


    public Long getEventTriggerId() {
		return eventTriggerId;
	}

	public void setEventTriggerId(Long eventTriggerId) {
		this.eventTriggerId = eventTriggerId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlowDto)) {
            return false;
        }
        return id != null && id.equals(((FlowDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Flow{" +
            "id=" + getId() +
            ", eventTriggerId='" + getEventTriggerId() + "'" +
            ", flowName='" + getFlowName() + "'" +
            ", flowDescription='" + getFlowDescription() + "'" +
            ", nextExecutionStartTime='" + getNextExecutionStartTime() + "'" +
            ", flowState='" + getFlowState() + "'" +
            "}";
    }
}
