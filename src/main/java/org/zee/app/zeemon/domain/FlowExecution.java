package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.ExecutionStatus;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A FlowExecution.
 */
@Entity
@Table(name = "flow_execution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FlowExecution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "execution_start_time", nullable = false)
    private Instant executionStartTime;

    @NotNull
    @Column(name = "execution_end_time", nullable = false)
    private Instant executionEndTime;

    @Min(value = 0)
    @Column(name = "total_task_count")
    private Integer totalTaskCount;

    @Min(value = 0)
    @Column(name = "running_task_count")
    private Integer runningTaskCount;

    @Min(value = 0)
    @Column(name = "error_task_count")
    private Integer errorTaskCount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "execution_status", nullable = false)
    private ExecutionStatus executionStatus;

    @OneToMany(mappedBy = "flowExecution", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TaskExecution> taskExecutions = new HashSet<>();

    @OneToMany(mappedBy = "flowExecution", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Content> contents = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("flowExecutions")
    private Flow flow;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExecutionStartTime() {
        return executionStartTime;
    }

    public FlowExecution executionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
        return this;
    }

    public void setExecutionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public Instant getExecutionEndTime() {
        return executionEndTime;
    }

    public FlowExecution executionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
        return this;
    }

    public void setExecutionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
    }

    public Integer getTotalTaskCount() {
        return totalTaskCount;
    }

    public FlowExecution totalTaskCount(Integer totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
        return this;
    }

    public void setTotalTaskCount(Integer totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
    }

    public Integer getRunningTaskCount() {
        return runningTaskCount;
    }

    public FlowExecution runningTaskCount(Integer runningTaskCount) {
        this.runningTaskCount = runningTaskCount;
        return this;
    }

    public void setRunningTaskCount(Integer runningTaskCount) {
        this.runningTaskCount = runningTaskCount;
    }

    public Integer getErrorTaskCount() {
        return errorTaskCount;
    }

    public FlowExecution errorTaskCount(Integer errorTaskCount) {
        this.errorTaskCount = errorTaskCount;
        return this;
    }

    public void setErrorTaskCount(Integer errorTaskCount) {
        this.errorTaskCount = errorTaskCount;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public FlowExecution executionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
        return this;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Set<TaskExecution> getTaskExecutions() {
        return taskExecutions;
    }

    public FlowExecution taskExecutions(Set<TaskExecution> taskExecutions) {
        this.taskExecutions = taskExecutions;
        return this;
    }

    public FlowExecution addTaskExecution(TaskExecution taskExecution) {
        this.taskExecutions.add(taskExecution);
        taskExecution.setFlowExecution(this);
        return this;
    }

    public FlowExecution removeTaskExecution(TaskExecution taskExecution) {
        this.taskExecutions.remove(taskExecution);
        taskExecution.setFlowExecution(null);
        return this;
    }

    public void setTaskExecutions(Set<TaskExecution> taskExecutions) {
        this.taskExecutions = taskExecutions;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public FlowExecution contents(Set<Content> contents) {
        this.contents = contents;
        return this;
    }

    public FlowExecution addContent(Content content) {
        this.contents.add(content);
        content.setFlowExecution(this);
        return this;
    }

    public FlowExecution removeContent(Content content) {
        this.contents.remove(content);
        content.setFlowExecution(null);
        return this;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    public Flow getFlow() {
        return flow;
    }

    public FlowExecution flow(Flow flow) {
        this.flow = flow;
        return this;
    }

    public void setFlow(Flow flow) {
        this.flow = flow;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlowExecution)) {
            return false;
        }
        return id != null && id.equals(((FlowExecution) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FlowExecution{" +
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
