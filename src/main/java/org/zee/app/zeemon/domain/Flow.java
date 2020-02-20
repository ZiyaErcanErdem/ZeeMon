package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.FlowState;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Flow.
 */
@Entity
@Table(name = "flow")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Flow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "flow_name", length = 255, nullable = false)
    private String flowName;

    @Size(max = 512)
    @Column(name = "flow_description", length = 512)
    private String flowDescription;

    @Column(name = "next_execution_start_time")
    private Instant nextExecutionStartTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "flow_state", nullable = false)
    private FlowState flowState;

    @OneToMany(mappedBy = "flow")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FlowExecution> flowExecutions = new HashSet<>();

    @OneToMany(mappedBy = "flow")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "flow")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Content> contents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("flows")
    private EventTrigger eventTrigger;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlowName() {
        return flowName;
    }

    public Flow flowName(String flowName) {
        this.flowName = flowName;
        return this;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getFlowDescription() {
        return flowDescription;
    }

    public Flow flowDescription(String flowDescription) {
        this.flowDescription = flowDescription;
        return this;
    }

    public void setFlowDescription(String flowDescription) {
        this.flowDescription = flowDescription;
    }

    public Instant getNextExecutionStartTime() {
        return nextExecutionStartTime;
    }

    public Flow nextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
        return this;
    }

    public void setNextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
    }

    public FlowState getFlowState() {
        return flowState;
    }

    public Flow flowState(FlowState flowState) {
        this.flowState = flowState;
        return this;
    }

    public void setFlowState(FlowState flowState) {
        this.flowState = flowState;
    }

    public Set<FlowExecution> getFlowExecutions() {
        return flowExecutions;
    }

    public Flow flowExecutions(Set<FlowExecution> flowExecutions) {
        this.flowExecutions = flowExecutions;
        return this;
    }

    public Flow addFlowExecution(FlowExecution flowExecution) {
        this.flowExecutions.add(flowExecution);
        flowExecution.setFlow(this);
        return this;
    }

    public Flow removeFlowExecution(FlowExecution flowExecution) {
        this.flowExecutions.remove(flowExecution);
        flowExecution.setFlow(null);
        return this;
    }

    public void setFlowExecutions(Set<FlowExecution> flowExecutions) {
        this.flowExecutions = flowExecutions;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Flow tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Flow addTask(Task task) {
        this.tasks.add(task);
        task.setFlow(this);
        return this;
    }

    public Flow removeTask(Task task) {
        this.tasks.remove(task);
        task.setFlow(null);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public Flow contents(Set<Content> contents) {
        this.contents = contents;
        return this;
    }

    public Flow addContent(Content content) {
        this.contents.add(content);
        content.setFlow(this);
        return this;
    }

    public Flow removeContent(Content content) {
        this.contents.remove(content);
        content.setFlow(null);
        return this;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    public EventTrigger getEventTrigger() {
        return eventTrigger;
    }

    public Flow eventTrigger(EventTrigger eventTrigger) {
        this.eventTrigger = eventTrigger;
        return this;
    }

    public void setEventTrigger(EventTrigger eventTrigger) {
        this.eventTrigger = eventTrigger;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Flow)) {
            return false;
        }
        return id != null && id.equals(((Flow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Flow{" +
            "id=" + getId() +
            ", flowName='" + getFlowName() + "'" +
            ", flowDescription='" + getFlowDescription() + "'" +
            ", nextExecutionStartTime='" + getNextExecutionStartTime() + "'" +
            ", flowState='" + getFlowState() + "'" +
            "}";
    }
}
