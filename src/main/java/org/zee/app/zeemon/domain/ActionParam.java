package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.DataType;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ActionParam.
 */
@Entity
@Table(name = "action_param")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActionParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "param_name", length = 255, nullable = false)
    private String paramName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "param_data_type", nullable = false)
    private DataType paramDataType;

    @Size(max = 255)
    @Column(name = "param_value", length = 255)
    private String paramValue;

    @Size(max = 255)
    @Column(name = "param_expression", length = 255)
    private String paramExpression;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("actionParams")
    private ActionScript actionScript;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public ActionParam paramName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public DataType getParamDataType() {
        return paramDataType;
    }

    public ActionParam paramDataType(DataType paramDataType) {
        this.paramDataType = paramDataType;
        return this;
    }

    public void setParamDataType(DataType paramDataType) {
        this.paramDataType = paramDataType;
    }

    public String getParamValue() {
        return paramValue;
    }

    public ActionParam paramValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamExpression() {
        return paramExpression;
    }

    public ActionParam paramExpression(String paramExpression) {
        this.paramExpression = paramExpression;
        return this;
    }

    public void setParamExpression(String paramExpression) {
        this.paramExpression = paramExpression;
    }

    public ActionScript getActionScript() {
        return actionScript;
    }

    public ActionParam actionScript(ActionScript actionScript) {
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
        if (!(o instanceof ActionParam)) {
            return false;
        }
        return id != null && id.equals(((ActionParam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ActionParam{" +
            "id=" + getId() +
            ", paramName='" + getParamName() + "'" +
            ", paramDataType='" + getParamDataType() + "'" +
            ", paramValue='" + getParamValue() + "'" +
            ", paramExpression='" + getParamExpression() + "'" +
            "}";
    }
}
