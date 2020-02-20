package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.ActionState;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "action_name", length = 255, nullable = false)
    private String actionName;

    @Size(max = 512)
    @Column(name = "action_description", length = 512)
    private String actionDescription;

    @Column(name = "next_execution_start_time")
    private Instant nextExecutionStartTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_state")
    private ActionState actionState;

    @Size(max = 2048)
    @Column(name = "resolution_rule_expression", length = 2048)
    private String resolutionRuleExpression;

    @OneToMany(mappedBy = "action")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActionExecution> actionExecutions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("actions")
    private Agent agent;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("actions")
    private ActionScript actionScript;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionName() {
        return actionName;
    }

    public Action actionName(String actionName) {
        this.actionName = actionName;
        return this;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public Action actionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
        return this;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public Instant getNextExecutionStartTime() {
        return nextExecutionStartTime;
    }

    public Action nextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
        return this;
    }

    public void setNextExecutionStartTime(Instant nextExecutionStartTime) {
        this.nextExecutionStartTime = nextExecutionStartTime;
    }

    public ActionState getActionState() {
        return actionState;
    }

    public Action actionState(ActionState actionState) {
        this.actionState = actionState;
        return this;
    }

    public void setActionState(ActionState actionState) {
        this.actionState = actionState;
    }

    public String getResolutionRuleExpression() {
        return resolutionRuleExpression;
    }

    public Action resolutionRuleExpression(String resolutionRuleExpression) {
        this.resolutionRuleExpression = resolutionRuleExpression;
        return this;
    }

    public void setResolutionRuleExpression(String resolutionRuleExpression) {
        this.resolutionRuleExpression = resolutionRuleExpression;
    }

    public Set<ActionExecution> getActionExecutions() {
        return actionExecutions;
    }

    public Action actionExecutions(Set<ActionExecution> actionExecutions) {
        this.actionExecutions = actionExecutions;
        return this;
    }

    public Action addActionExecution(ActionExecution actionExecution) {
        this.actionExecutions.add(actionExecution);
        actionExecution.setAction(this);
        return this;
    }

    public Action removeActionExecution(ActionExecution actionExecution) {
        this.actionExecutions.remove(actionExecution);
        actionExecution.setAction(null);
        return this;
    }

    public void setActionExecutions(Set<ActionExecution> actionExecutions) {
        this.actionExecutions = actionExecutions;
    }

    public Agent getAgent() {
        return agent;
    }

    public Action agent(Agent agent) {
        this.agent = agent;
        return this;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public ActionScript getActionScript() {
        return actionScript;
    }

    public Action actionScript(ActionScript actionScript) {
        this.actionScript = actionScript;
        return this;
    }

    public void setActionScript(ActionScript actionScript) {
        this.actionScript = actionScript;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Action)) {
            return false;
        }
        return id != null && id.equals(((Action) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", actionName='" + getActionName() + "'" +
            ", actionDescription='" + getActionDescription() + "'" +
            ", nextExecutionStartTime='" + getNextExecutionStartTime() + "'" +
            ", actionState='" + getActionState() + "'" +
            ", resolutionRuleExpression='" + getResolutionRuleExpression() + "'" +
            "}";
    }
}
