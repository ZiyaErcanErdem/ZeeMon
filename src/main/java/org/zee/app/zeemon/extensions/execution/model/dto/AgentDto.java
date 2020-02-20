package org.zee.app.zeemon.extensions.execution.model.dto;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.zee.app.zeemon.domain.enumeration.AgentStatus;
import org.zee.app.zeemon.domain.enumeration.AgentType;


public class AgentDto{

    private Long id;

    @NotNull
    @Size(max = 100)
    private String agentName;

    @Size(max = 100)
    private String agentInstanceId;

    @Size(max = 512)
    private String listenURI;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AgentType agentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AgentStatus agentStatus;

    @Size(max = 512)
    private String agentDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public AgentDto agentName(String agentName) {
        this.agentName = agentName;
        return this;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentInstanceId() {
        return agentInstanceId;
    }

    public AgentDto agentInstanceId(String agentInstanceId) {
        this.agentInstanceId = agentInstanceId;
        return this;
    }

    public void setAgentInstanceId(String agentInstanceId) {
        this.agentInstanceId = agentInstanceId;
    }

    public String getListenURI() {
        return listenURI;
    }

    public AgentDto listenURI(String listenURI) {
        this.listenURI = listenURI;
        return this;
    }

    public void setListenURI(String listenURI) {
        this.listenURI = listenURI;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public AgentDto agentType(AgentType agentType) {
        this.agentType = agentType;
        return this;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public AgentStatus getAgentStatus() {
        return agentStatus;
    }

    public AgentDto agentStatus(AgentStatus agentStatus) {
        this.agentStatus = agentStatus;
        return this;
    }

    public void setAgentStatus(AgentStatus agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getAgentDescription() {
        return agentDescription;
    }

    public AgentDto agentDescription(String agentDescription) {
        this.agentDescription = agentDescription;
        return this;
    }

    public void setAgentDescription(String agentDescription) {
        this.agentDescription = agentDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgentDto)) {
            return false;
        }
        return id != null && id.equals(((AgentDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Agent{" +
            "id=" + getId() +
            ", agentName='" + getAgentName() + "'" +
            ", agentInstanceId='" + getAgentInstanceId() + "'" +
            ", listenURI='" + getListenURI() + "'" +
            ", agentType='" + getAgentType() + "'" +
            ", agentStatus='" + getAgentStatus() + "'" +
            ", agentDescription='" + getAgentDescription() + "'" +
            "}";
    }
}
