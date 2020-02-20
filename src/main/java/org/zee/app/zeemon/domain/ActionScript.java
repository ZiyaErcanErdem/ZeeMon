package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.ActionType;
import org.zee.app.zeemon.domain.enumeration.ScriptType;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ActionScript.
 */
@Entity
@Table(name = "action_script")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActionScript implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "action_code", length = 20, nullable = false)
    private String actionCode;

    @NotNull
    @Size(max = 255)
    @Column(name = "script_name", length = 255, nullable = false)
    private String scriptName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "script_type", nullable = false)
    private ScriptType scriptType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    @Size(max = 2048)
    @Column(name = "action_source", length = 2048)
    private String actionSource;

    @OneToMany(mappedBy = "actionScript", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActionParam> actionParams = new HashSet<>();

    @OneToMany(mappedBy = "actionScript", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Action> actions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("actionScripts")
    private Endpoint endpoint;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionCode() {
        return actionCode;
    }

    public ActionScript actionCode(String actionCode) {
        this.actionCode = actionCode;
        return this;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getScriptName() {
        return scriptName;
    }

    public ActionScript scriptName(String scriptName) {
        this.scriptName = scriptName;
        return this;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public ScriptType getScriptType() {
        return scriptType;
    }

    public ActionScript scriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
        return this;
    }

    public void setScriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public ActionScript actionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getActionSource() {
        return actionSource;
    }

    public ActionScript actionSource(String actionSource) {
        this.actionSource = actionSource;
        return this;
    }

    public void setActionSource(String actionSource) {
        this.actionSource = actionSource;
    }

    public Set<ActionParam> getActionParams() {
        return actionParams;
    }

    public ActionScript actionParams(Set<ActionParam> actionParams) {
        this.actionParams = actionParams;
        return this;
    }

    public ActionScript addActionParam(ActionParam actionParam) {
        this.actionParams.add(actionParam);
        actionParam.setActionScript(this);
        return this;
    }

    public ActionScript removeActionParam(ActionParam actionParam) {
        this.actionParams.remove(actionParam);
        actionParam.setActionScript(null);
        return this;
    }

    public void setActionParams(Set<ActionParam> actionParams) {
        this.actionParams = actionParams;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public ActionScript actions(Set<Action> actions) {
        this.actions = actions;
        return this;
    }

    public ActionScript addAction(Action action) {
        this.actions.add(action);
        action.setActionScript(this);
        return this;
    }

    public ActionScript removeAction(Action action) {
        this.actions.remove(action);
        action.setActionScript(null);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public ActionScript endpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActionScript)) {
            return false;
        }
        return id != null && id.equals(((ActionScript) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ActionScript{" +
            "id=" + getId() +
            ", actionCode='" + getActionCode() + "'" +
            ", scriptName='" + getScriptName() + "'" +
            ", scriptType='" + getScriptType() + "'" +
            ", actionType='" + getActionType() + "'" +
            ", actionSource='" + getActionSource() + "'" +
            "}";
    }
}
