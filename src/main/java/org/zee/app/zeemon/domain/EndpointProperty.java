package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.DataType;
import org.zee.app.zeemon.domain.enumeration.PropKeyType;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A EndpointProperty.
 */
@Entity
@Table(name = "endpoint_property")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EndpointProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "prop_key", length = 100, nullable = false)
    private String propKey;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prop_key_type", nullable = false)
    private PropKeyType propKeyType;

    @NotNull
    @Size(max = 512)
    @Column(name = "prop_value", length = 512, nullable = false)
    private String propValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prop_value_type", nullable = false)
    private DataType propValueType;

    @Size(max = 512)
    @Column(name = "prop_description", length = 512)
    private String propDescription;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("endpointProperties")
    private Endpoint endpoint;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropKey() {
        return propKey;
    }

    public EndpointProperty propKey(String propKey) {
        this.propKey = propKey;
        return this;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public PropKeyType getPropKeyType() {
        return propKeyType;
    }

    public EndpointProperty propKeyType(PropKeyType propKeyType) {
        this.propKeyType = propKeyType;
        return this;
    }

    public void setPropKeyType(PropKeyType propKeyType) {
        this.propKeyType = propKeyType;
    }

    public String getPropValue() {
        return propValue;
    }

    public EndpointProperty propValue(String propValue) {
        this.propValue = propValue;
        return this;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    public DataType getPropValueType() {
        return propValueType;
    }

    public EndpointProperty propValueType(DataType propValueType) {
        this.propValueType = propValueType;
        return this;
    }

    public void setPropValueType(DataType propValueType) {
        this.propValueType = propValueType;
    }

    public String getPropDescription() {
        return propDescription;
    }

    public EndpointProperty propDescription(String propDescription) {
        this.propDescription = propDescription;
        return this;
    }

    public void setPropDescription(String propDescription) {
        this.propDescription = propDescription;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public EndpointProperty endpoint(Endpoint endpoint) {
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
        if (!(o instanceof EndpointProperty)) {
            return false;
        }
        return id != null && id.equals(((EndpointProperty) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EndpointProperty{" +
            "id=" + getId() +
            ", propKey='" + getPropKey() + "'" +
            ", propKeyType='" + getPropKeyType() + "'" +
            ", propValue='" + getPropValue() + "'" +
            ", propValueType='" + getPropValueType() + "'" +
            ", propDescription='" + getPropDescription() + "'" +
            "}";
    }
}
