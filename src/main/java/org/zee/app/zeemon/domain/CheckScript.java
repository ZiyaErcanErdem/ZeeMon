package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.ScriptType;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CheckScript.
 */
@Entity
@Table(name = "check_script")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CheckScript implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "script_name", length = 255, nullable = false)
    private String scriptName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "script_type", nullable = false)
    private ScriptType scriptType;

    @Size(max = 2048)
    @Column(name = "script_source", length = 2048)
    private String scriptSource;

    @Size(max = 2048)
    @Column(name = "action_rule_expression", length = 2048)
    private String actionRuleExpression;

    @OneToMany(mappedBy = "checkScript", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ScriptParam> scriptParams = new HashSet<>();

    @OneToMany(mappedBy = "checkScript", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "checkScript", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Content> contents = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("checkScripts")
    private Endpoint endpoint;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("checkScripts")
    private ContentMapper contentMapper;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScriptName() {
        return scriptName;
    }

    public CheckScript scriptName(String scriptName) {
        this.scriptName = scriptName;
        return this;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public ScriptType getScriptType() {
        return scriptType;
    }

    public CheckScript scriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
        return this;
    }

    public void setScriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
    }

    public String getScriptSource() {
        return scriptSource;
    }

    public CheckScript scriptSource(String scriptSource) {
        this.scriptSource = scriptSource;
        return this;
    }

    public void setScriptSource(String scriptSource) {
        this.scriptSource = scriptSource;
    }

    public String getActionRuleExpression() {
        return actionRuleExpression;
    }

    public CheckScript actionRuleExpression(String actionRuleExpression) {
        this.actionRuleExpression = actionRuleExpression;
        return this;
    }

    public void setActionRuleExpression(String actionRuleExpression) {
        this.actionRuleExpression = actionRuleExpression;
    }

    public Set<ScriptParam> getScriptParams() {
        return scriptParams;
    }

    public CheckScript scriptParams(Set<ScriptParam> scriptParams) {
        this.scriptParams = scriptParams;
        return this;
    }

    public CheckScript addScriptParam(ScriptParam scriptParam) {
        this.scriptParams.add(scriptParam);
        scriptParam.setCheckScript(this);
        return this;
    }

    public CheckScript removeScriptParam(ScriptParam scriptParam) {
        this.scriptParams.remove(scriptParam);
        scriptParam.setCheckScript(null);
        return this;
    }

    public void setScriptParams(Set<ScriptParam> scriptParams) {
        this.scriptParams = scriptParams;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public CheckScript tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public CheckScript addTask(Task task) {
        this.tasks.add(task);
        task.setCheckScript(this);
        return this;
    }

    public CheckScript removeTask(Task task) {
        this.tasks.remove(task);
        task.setCheckScript(null);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public CheckScript contents(Set<Content> contents) {
        this.contents = contents;
        return this;
    }

    public CheckScript addContent(Content content) {
        this.contents.add(content);
        content.setCheckScript(this);
        return this;
    }

    public CheckScript removeContent(Content content) {
        this.contents.remove(content);
        content.setCheckScript(null);
        return this;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public CheckScript endpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public ContentMapper getContentMapper() {
        return contentMapper;
    }

    public CheckScript contentMapper(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
        return this;
    }

    public void setContentMapper(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckScript)) {
            return false;
        }
        return id != null && id.equals(((CheckScript) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CheckScript{" +
            "id=" + getId() +
            ", scriptName='" + getScriptName() + "'" +
            ", scriptType='" + getScriptType() + "'" +
            ", scriptSource='" + getScriptSource() + "'" +
            ", actionRuleExpression='" + getActionRuleExpression() + "'" +
            "}";
    }
}
