package org.zee.app.zeemon.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.EndpointSpec;
import org.zee.app.zeemon.domain.enumeration.EndpointType;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Endpoint.
 */
@Entity
@Table(name = "endpoint")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Endpoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "endpoint_name", length = 100, nullable = false)
    private String endpointName;

    @Size(max = 100)
    @Column(name = "endpoint_instance_id", length = 100)
    private String endpointInstanceId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "endpoint_type", nullable = false)
    private EndpointType endpointType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "endpoint_spec", nullable = false)
    private EndpointSpec endpointSpec;

    @Size(max = 512)
    @Column(name = "endpoint_description", length = 512)
    private String endpointDescription;

    @OneToMany(mappedBy = "endpoint", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EndpointProperty> endpointProperties = new HashSet<>();

    @OneToMany(mappedBy = "endpoint")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CheckScript> checkScripts = new HashSet<>();

    @OneToMany(mappedBy = "endpoint")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActionScript> actionScripts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public Endpoint endpointName(String endpointName) {
        this.endpointName = endpointName;
        return this;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getEndpointInstanceId() {
        return endpointInstanceId;
    }

    public Endpoint endpointInstanceId(String endpointInstanceId) {
        this.endpointInstanceId = endpointInstanceId;
        return this;
    }

    public void setEndpointInstanceId(String endpointInstanceId) {
        this.endpointInstanceId = endpointInstanceId;
    }

    public EndpointType getEndpointType() {
        return endpointType;
    }

    public Endpoint endpointType(EndpointType endpointType) {
        this.endpointType = endpointType;
        return this;
    }

    public void setEndpointType(EndpointType endpointType) {
        this.endpointType = endpointType;
    }

    public EndpointSpec getEndpointSpec() {
        return endpointSpec;
    }

    public Endpoint endpointSpec(EndpointSpec endpointSpec) {
        this.endpointSpec = endpointSpec;
        return this;
    }

    public void setEndpointSpec(EndpointSpec endpointSpec) {
        this.endpointSpec = endpointSpec;
    }

    public String getEndpointDescription() {
        return endpointDescription;
    }

    public Endpoint endpointDescription(String endpointDescription) {
        this.endpointDescription = endpointDescription;
        return this;
    }

    public void setEndpointDescription(String endpointDescription) {
        this.endpointDescription = endpointDescription;
    }

    public Set<EndpointProperty> getEndpointProperties() {
        return endpointProperties;
    }

    public Endpoint endpointProperties(Set<EndpointProperty> endpointProperties) {
        this.endpointProperties = endpointProperties;
        return this;
    }

    public Endpoint addEndpointProperty(EndpointProperty endpointProperty) {
        this.endpointProperties.add(endpointProperty);
        endpointProperty.setEndpoint(this);
        return this;
    }

    public Endpoint removeEndpointProperty(EndpointProperty endpointProperty) {
        this.endpointProperties.remove(endpointProperty);
        endpointProperty.setEndpoint(null);
        return this;
    }

    public void setEndpointProperties(Set<EndpointProperty> endpointProperties) {
        this.endpointProperties = endpointProperties;
    }

    public Set<CheckScript> getCheckScripts() {
        return checkScripts;
    }

    public Endpoint checkScripts(Set<CheckScript> checkScripts) {
        this.checkScripts = checkScripts;
        return this;
    }

    public Endpoint addCheckScript(CheckScript checkScript) {
        this.checkScripts.add(checkScript);
        checkScript.setEndpoint(this);
        return this;
    }

    public Endpoint removeCheckScript(CheckScript checkScript) {
        this.checkScripts.remove(checkScript);
        checkScript.setEndpoint(null);
        return this;
    }

    public void setCheckScripts(Set<CheckScript> checkScripts) {
        this.checkScripts = checkScripts;
    }

    public Set<ActionScript> getActionScripts() {
        return actionScripts;
    }

    public Endpoint actionScripts(Set<ActionScript> actionScripts) {
        this.actionScripts = actionScripts;
        return this;
    }

    public Endpoint addActionScript(ActionScript actionScript) {
        this.actionScripts.add(actionScript);
        actionScript.setEndpoint(this);
        return this;
    }

    public Endpoint removeActionScript(ActionScript actionScript) {
        this.actionScripts.remove(actionScript);
        actionScript.setEndpoint(null);
        return this;
    }

    public void setActionScripts(Set<ActionScript> actionScripts) {
        this.actionScripts = actionScripts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Endpoint)) {
            return false;
        }
        return id != null && id.equals(((Endpoint) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Endpoint{" +
            "id=" + getId() +
            ", endpointName='" + getEndpointName() + "'" +
            ", endpointInstanceId='" + getEndpointInstanceId() + "'" +
            ", endpointType='" + getEndpointType() + "'" +
            ", endpointSpec='" + getEndpointSpec() + "'" +
            ", endpointDescription='" + getEndpointDescription() + "'" +
            "}";
    }
}
