package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.TaskState;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "task_name", length = 255, nullable = false)
    private String taskName;

    @Size(max = 512)
    @Column(name = "task_description", length = 512)
    private String taskDescription;

    @Column(name = "next_execution_start_time")
    private Instant nextExecutionStartTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "task_state", nullable = false)
    private TaskState taskState;

    @OneToMany(mappedBy = "task")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TaskExecution> taskExecutions = new HashSet<>();

    @OneToMany(mappedBy = "task")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Content> contents = new HashSet<>();

    @OneToMany(mappedBy = "task")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ContentValidationError> contentValidationErrors = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("tasks")
    private Agent agent;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("tasks")
    private CheckScript checkScript;

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    private Flow flow;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public Task taskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public Task taskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
        return this;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Instant getNextExecutionStartTime() {
        return nextExecutionStartTime;
    }

    public Task nextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
        return this;
    }

    public void setNextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public Task taskState(TaskState taskState) {
        this.taskState = taskState;
        return this;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    public Set<TaskExecution> getTaskExecutions() {
        return taskExecutions;
    }

    public Task taskExecutions(Set<TaskExecution> taskExecutions) {
        this.taskExecutions = taskExecutions;
        return this;
    }

    public Task addTaskExecution(TaskExecution taskExecution) {
        this.taskExecutions.add(taskExecution);
        taskExecution.setTask(this);
        return this;
    }

    public Task removeTaskExecution(TaskExecution taskExecution) {
        this.taskExecutions.remove(taskExecution);
        taskExecution.setTask(null);
        return this;
    }

    public void setTaskExecutions(Set<TaskExecution> taskExecutions) {
        this.taskExecutions = taskExecutions;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public Task contents(Set<Content> contents) {
        this.contents = contents;
        return this;
    }

    public Task addContent(Content content) {
        this.contents.add(content);
        content.setTask(this);
        return this;
    }

    public Task removeContent(Content content) {
        this.contents.remove(content);
        content.setTask(null);
        return this;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    public Set<ContentValidationError> getContentValidationErrors() {
        return contentValidationErrors;
    }

    public Task contentValidationErrors(Set<ContentValidationError> contentValidationErrors) {
        this.contentValidationErrors = contentValidationErrors;
        return this;
    }

    public Task addContentValidationError(ContentValidationError contentValidationError) {
        this.contentValidationErrors.add(contentValidationError);
        contentValidationError.setTask(this);
        return this;
    }

    public Task removeContentValidationError(ContentValidationError contentValidationError) {
        this.contentValidationErrors.remove(contentValidationError);
        contentValidationError.setTask(null);
        return this;
    }

    public void setContentValidationErrors(Set<ContentValidationError> contentValidationErrors) {
        this.contentValidationErrors = contentValidationErrors;
    }

    public Agent getAgent() {
        return agent;
    }

    public Task agent(Agent agent) {
        this.agent = agent;
        return this;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public CheckScript getCheckScript() {
        return checkScript;
    }

    public Task checkScript(CheckScript checkScript) {
        this.checkScript = checkScript;
        return this;
    }

    public void setCheckScript(CheckScript checkScript) {
        this.checkScript = checkScript;
    }

    public Flow getFlow() {
        return flow;
    }

    public Task flow(Flow flow) {
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
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", taskName='" + getTaskName() + "'" +
            ", taskDescription='" + getTaskDescription() + "'" +
            ", nextExecutionStartTime='" + getNextExecutionStartTime() + "'" +
            ", taskState='" + getTaskState() + "'" +
            "}";
    }
}
