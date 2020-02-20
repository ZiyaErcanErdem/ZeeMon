package org.zee.app.zeemon.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.ItemFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ContentMapper.
 */
@Entity
@Table(name = "content_mapper")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "mapper_name", length = 255, nullable = false)
    private String mapperName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_format", nullable = false)
    private ItemFormat itemFormat;

    @Column(name = "contains_header")
    private Boolean containsHeader;

    @NotNull
    @Size(max = 50)
    @Column(name = "field_delimiter", length = 50, nullable = false)
    private String fieldDelimiter;

    @OneToMany(mappedBy = "contentMapper")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CheckScript> checkScripts = new HashSet<>();

    @OneToMany(mappedBy = "contentMapper", orphanRemoval=true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FieldMapping> fieldMappings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMapperName() {
        return mapperName;
    }

    public ContentMapper mapperName(String mapperName) {
        this.mapperName = mapperName;
        return this;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public ItemFormat getItemFormat() {
        return itemFormat;
    }

    public ContentMapper itemFormat(ItemFormat itemFormat) {
        this.itemFormat = itemFormat;
        return this;
    }

    public void setItemFormat(ItemFormat itemFormat) {
        this.itemFormat = itemFormat;
    }

    public Boolean isContainsHeader() {
        return containsHeader;
    }

    public ContentMapper containsHeader(Boolean containsHeader) {
        this.containsHeader = containsHeader;
        return this;
    }

    public void setContainsHeader(Boolean containsHeader) {
        this.containsHeader = containsHeader;
    }

    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public ContentMapper fieldDelimiter(String fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
        return this;
    }

    public void setFieldDelimiter(String fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
    }

    public Set<CheckScript> getCheckScripts() {
        return checkScripts;
    }

    public ContentMapper checkScripts(Set<CheckScript> checkScripts) {
        this.checkScripts = checkScripts;
        return this;
    }

    public ContentMapper addCheckScript(CheckScript checkScript) {
        this.checkScripts.add(checkScript);
        checkScript.setContentMapper(this);
        return this;
    }

    public ContentMapper removeCheckScript(CheckScript checkScript) {
        this.checkScripts.remove(checkScript);
        checkScript.setContentMapper(null);
        return this;
    }

    public void setCheckScripts(Set<CheckScript> checkScripts) {
        this.checkScripts = checkScripts;
    }

    public Set<FieldMapping> getFieldMappings() {
        return fieldMappings;
    }

    public ContentMapper fieldMappings(Set<FieldMapping> fieldMappings) {
        this.fieldMappings = fieldMappings;
        return this;
    }

    public ContentMapper addFieldMapping(FieldMapping fieldMapping) {
        this.fieldMappings.add(fieldMapping);
        fieldMapping.setContentMapper(this);
        return this;
    }

    public ContentMapper removeFieldMapping(FieldMapping fieldMapping) {
        this.fieldMappings.remove(fieldMapping);
        fieldMapping.setContentMapper(null);
        return this;
    }

    public void setFieldMappings(Set<FieldMapping> fieldMappings) {
        this.fieldMappings = fieldMappings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentMapper)) {
            return false;
        }
        return id != null && id.equals(((ContentMapper) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContentMapper{" +
            "id=" + getId() +
            ", mapperName='" + getMapperName() + "'" +
            ", itemFormat='" + getItemFormat() + "'" +
            ", containsHeader='" + isContainsHeader() + "'" +
            ", fieldDelimiter='" + getFieldDelimiter() + "'" +
            "}";
    }
}
