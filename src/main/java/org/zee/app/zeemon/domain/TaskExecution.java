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
 * A TaskExecution.
 */
@Entity
@Table(name = "task_execution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaskExecution implements Serializable {

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "execution_status", nullable = false)
    private ExecutionStatus executionStatus;

    @OneToMany(mappedBy = "taskExecution", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Content> contents = new HashSet<>();

    @OneToMany(mappedBy = "taskExecution", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContentValidationError> contentValidationErrors = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("taskExecutions")
    private Task task;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("taskExecutions")
    private FlowExecution flowExecution;

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

    public TaskExecution executionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
        return this;
    }

    public void setExecutionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public Instant getExecutionEndTime() {
        return executionEndTime;
    }

    public TaskExecution executionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
        return this;
    }

    public void setExecutionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public TaskExecution executionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
        return this;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public TaskExecution contents(Set<Content> contents) {
        this.contents = contents;
        return this;
    }

    public TaskExecution addContent(Content content) {
        this.contents.add(content);
        content.setTaskExecution(this);
        return this;
    }

    public TaskExecution removeContent(Content content) {
        this.contents.remove(content);
        content.setTaskExecution(null);
        return this;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    public Set<ContentValidationError> getContentValidationErrors() {
        return contentValidationErrors;
    }

    public TaskExecution contentValidationErrors(Set<ContentValidationError> contentValidationErrors) {
        this.contentValidationErrors = contentValidationErrors;
        return this;
    }

    public TaskExecution addContentValidationError(ContentValidationError contentValidationError) {
        this.contentValidationErrors.add(contentValidationError);
        contentValidationError.setTaskExecution(this);
        return this;
    }

    public TaskExecution removeContentValidationError(ContentValidationError contentValidationError) {
        this.contentValidationErrors.remove(contentValidationError);
        contentValidationError.setTaskExecution(null);
        return this;
    }

    public void setContentValidationErrors(Set<ContentValidationError> contentValidationErrors) {
        this.contentValidationErrors = contentValidationErrors;
    }

    public Task getTask() {
        return task;
    }

    public TaskExecution task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public FlowExecution getFlowExecution() {
        return flowExecution;
    }

    public TaskExecution flowExecution(FlowExecution flowExecution) {
        this.flowExecution = flowExecution;
        return this;
    }

    public void setFlowExecution(FlowExecution flowExecution) {
        this.flowExecution = flowExecution;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskExecution)) {
            return false;
        }
        return id != null && id.equals(((TaskExecution) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TaskExecution{" +
            "id=" + getId() +
            ", executionStartTime='" + getExecutionStartTime() + "'" +
            ", executionEndTime='" + getExecutionEndTime() + "'" +
            ", executionStatus='" + getExecutionStatus() + "'" +
            "}";
    }
}
