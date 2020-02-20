package org.zee.app.zeemon.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.TimeUnit;
import org.zee.app.zeemon.domain.enumeration.TriggerType;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EventTrigger.
 */
@Entity
@Table(name = "event_trigger")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EventTrigger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "trigger_name", length = 100, nullable = false)
    private String triggerName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_type", nullable = false)
    private TriggerType triggerType;

    @Column(name = "trigger_period")
    private Long triggerPeriod;

    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_time_unit")
    private TimeUnit triggerTimeUnit;

    @Size(max = 255)
    @Column(name = "trigger_cron_expression", length = 255)
    private String triggerCronExpression;

    @Size(max = 255)
    @Column(name = "trigger_cron_time_zone", length = 255)
    private String triggerCronTimeZone;

    @OneToMany(mappedBy = "eventTrigger")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Flow> flows = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public EventTrigger triggerName(String triggerName) {
        this.triggerName = triggerName;
        return this;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public EventTrigger triggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
        return this;
    }

    public void setTriggerType(TriggerType triggerType) {
        this.triggerType = triggerType;
    }

    public Long getTriggerPeriod() {
        return triggerPeriod;
    }

    public EventTrigger triggerPeriod(Long triggerPeriod) {
        this.triggerPeriod = triggerPeriod;
        return this;
    }

    public void setTriggerPeriod(Long triggerPeriod) {
        this.triggerPeriod = triggerPeriod;
    }

    public TimeUnit getTriggerTimeUnit() {
        return triggerTimeUnit;
    }

    public EventTrigger triggerTimeUnit(TimeUnit triggerTimeUnit) {
        this.triggerTimeUnit = triggerTimeUnit;
        return this;
    }

    public void setTriggerTimeUnit(TimeUnit triggerTimeUnit) {
        this.triggerTimeUnit = triggerTimeUnit;
    }

    public String getTriggerCronExpression() {
        return triggerCronExpression;
    }

    public EventTrigger triggerCronExpression(String triggerCronExpression) {
        this.triggerCronExpression = triggerCronExpression;
        return this;
    }

    public void setTriggerCronExpression(String triggerCronExpression) {
        this.triggerCronExpression = triggerCronExpression;
    }

    public String getTriggerCronTimeZone() {
        return triggerCronTimeZone;
    }

    public EventTrigger triggerCronTimeZone(String triggerCronTimeZone) {
        this.triggerCronTimeZone = triggerCronTimeZone;
        return this;
    }

    public void setTriggerCronTimeZone(String triggerCronTimeZone) {
        this.triggerCronTimeZone = triggerCronTimeZone;
    }

    public Set<Flow> getFlows() {
        return flows;
    }

    public EventTrigger flows(Set<Flow> flows) {
        this.flows = flows;
        return this;
    }

    public EventTrigger addFlow(Flow flow) {
        this.flows.add(flow);
        flow.setEventTrigger(this);
        return this;
    }

    public EventTrigger removeFlow(Flow flow) {
        this.flows.remove(flow);
        flow.setEventTrigger(null);
        return this;
    }

    public void setFlows(Set<Flow> flows) {
        this.flows = flows;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventTrigger)) {
            return false;
        }
        return id != null && id.equals(((EventTrigger) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EventTrigger{" +
            "id=" + getId() +
            ", triggerName='" + getTriggerName() + "'" +
            ", triggerType='" + getTriggerType() + "'" +
            ", triggerPeriod=" + getTriggerPeriod() +
            ", triggerTimeUnit='" + getTriggerTimeUnit() + "'" +
            ", triggerCronExpression='" + getTriggerCronExpression() + "'" +
            ", triggerCronTimeZone='" + getTriggerCronTimeZone() + "'" +
            "}";
    }
}
