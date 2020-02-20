package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.ExecutionStatus;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ActionExecution.
 */
@Entity
@Table(name = "action_execution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActionExecution implements Serializable {

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

    @Size(max = 2048)
    @Column(name = "action_log", length = 2048)
    private String actionLog;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("actionExecutions")
    private Action action;

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

    public ActionExecution executionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
        return this;
    }

    public void setExecutionStartTime(Instant executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public Instant getExecutionEndTime() {
        return executionEndTime;
    }

    public ActionExecution executionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
        return this;
    }

    public void setExecutionEndTime(Instant executionEndTime) {
        this.executionEndTime = executionEndTime;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public ActionExecution executionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
        return this;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getActionLog() {
        return actionLog;
    }

    public ActionExecution actionLog(String actionLog) {
        this.actionLog = actionLog;
        return this;
    }

    public void setActionLog(String actionLog) {
        this.actionLog = actionLog;
    }

    public Action getAction() {
        return action;
    }

    public ActionExecution action(Action action) {
        this.action = action;
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionExecution)) {
            return false;
        }
        return id != null && id.equals(((ActionExecution) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ActionExecution{" +
            "id=" + getId() +
            ", executionStartTime='" + getExecutionStartTime() + "'" +
            ", executionEndTime='" + getExecutionEndTime() + "'" +
            ", executionStatus='" + getExecutionStatus() + "'" +
            ", actionLog='" + getActionLog() + "'" +
            "}";
    }
}
