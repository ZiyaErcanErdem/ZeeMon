package org.zee.app.zeemon.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.AgentStatus;
import org.zee.app.zeemon.domain.enumeration.AgentType;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Agent.
 */
@Entity
@Table(name = "agent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "agent_name", length = 100, nullable = false)
    private String agentName;

    @Size(max = 100)
    @Column(name = "agent_instance_id", length = 100)
    private String agentInstanceId;

    @Size(max = 512)
    @Column(name = "listen_uri", length = 512)
    private String listenURI;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "agent_type", nullable = false)
    private AgentType agentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "agent_status", nullable = false)
    private AgentStatus agentStatus;

    @Size(max = 512)
    @Column(name = "agent_description", length = 512)
    private String agentDescription;

    @OneToMany(mappedBy = "agent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "agent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Action> actions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public Agent agentName(String agentName) {
        this.agentName = agentName;
        return this;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentInstanceId() {
        return agentInstanceId;
    }

    public Agent agentInstanceId(String agentInstanceId) {
        this.agentInstanceId = agentInstanceId;
        return this;
    }

    public void setAgentInstanceId(String agentInstanceId) {
        this.agentInstanceId = agentInstanceId;
    }

    public String getListenURI() {
        return listenURI;
    }

    public Agent listenURI(String listenURI) {
        this.listenURI = listenURI;
        return this;
    }

    public void setListenURI(String listenURI) {
        this.listenURI = listenURI;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public Agent agentType(AgentType agentType) {
        this.agentType = agentType;
        return this;
    }

    public void setAgentType(AgentType agentType) {
        this.agentType = agentType;
    }

    public AgentStatus getAgentStatus() {
        return agentStatus;
    }

    public Agent agentStatus(AgentStatus agentStatus) {
        this.agentStatus = agentStatus;
        return this;
    }

    public void setAgentStatus(AgentStatus agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getAgentDescription() {
        return agentDescription;
    }

    public Agent agentDescription(String agentDescription) {
        this.agentDescription = agentDescription;
        return this;
    }

    public void setAgentDescription(String agentDescription) {
        this.agentDescription = agentDescription;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Agent tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Agent addTask(Task task) {
        this.tasks.add(task);
        task.setAgent(this);
        return this;
    }

    public Agent removeTask(Task task) {
        this.tasks.remove(task);
        task.setAgent(null);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public Agent actions(Set<Action> actions) {
        this.actions = actions;
        return this;
    }

    public Agent addAction(Action action) {
        this.actions.add(action);
        action.setAgent(this);
        return this;
    }

    public Agent removeAction(Action action) {
        this.actions.remove(action);
        action.setAgent(null);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agent)) {
            return false;
        }
        return id != null && id.equals(((Agent) o).id);
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
