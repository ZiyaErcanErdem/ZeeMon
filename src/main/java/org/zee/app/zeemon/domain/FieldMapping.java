package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zee.app.zeemon.domain.enumeration.DataType;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A FieldMapping.
 */
@Entity
@Table(name = "field_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 0)
    @Column(name = "source_index")
    private Integer sourceIndex;

    @Size(max = 255)
    @Column(name = "source_name", length = 255)
    private String sourceName;

    @Size(min = 0, max = 255)
    @Column(name = "source_format", length = 255)
    private String sourceFormat;

    @Min(value = 0)
    @Column(name = "source_start_index")
    private Integer sourceStartIndex;

    @Min(value = 0)
    @Column(name = "source_end_index")
    private Integer sourceEndIndex;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "source_data_type", nullable = false)
    private DataType sourceDataType;

    @NotNull
    @Size(max = 255)
    @Column(name = "target_name", length = 255, nullable = false)
    private String targetName;

    @NotNull
    @Size(max = 255)
    @Column(name = "target_col_name", length = 255, nullable = false)
    private String targetColName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "target_data_type", nullable = false)
    private DataType targetDataType;

    @Size(max = 2048)
    @Column(name = "transformation", length = 2048)
    private String transformation;

    @Column(name = "required_data")
    private Boolean requiredData;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("fieldMappings")
    private ContentMapper contentMapper;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSourceIndex() {
        return sourceIndex;
    }

    public FieldMapping sourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
        return this;
    }

    public void setSourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public String getSourceName() {
        return sourceName;
    }

    public FieldMapping sourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceFormat() {
        return sourceFormat;
    }

    public FieldMapping sourceFormat(String sourceFormat) {
        this.sourceFormat = sourceFormat;
        return this;
    }

    public void setSourceFormat(String sourceFormat) {
        this.sourceFormat = sourceFormat;
    }

    public Integer getSourceStartIndex() {
        return sourceStartIndex;
    }

    public FieldMapping sourceStartIndex(Integer sourceStartIndex) {
        this.sourceStartIndex = sourceStartIndex;
        return this;
    }

    public void setSourceStartIndex(Integer sourceStartIndex) {
        this.sourceStartIndex = sourceStartIndex;
    }

    public Integer getSourceEndIndex() {
        return sourceEndIndex;
    }

    public FieldMapping sourceEndIndex(Integer sourceEndIndex) {
        this.sourceEndIndex = sourceEndIndex;
        return this;
    }

    public void setSourceEndIndex(Integer sourceEndIndex) {
        this.sourceEndIndex = sourceEndIndex;
    }

    public DataType getSourceDataType() {
        return sourceDataType;
    }

    public FieldMapping sourceDataType(DataType sourceDataType) {
        this.sourceDataType = sourceDataType;
        return this;
    }

    public void setSourceDataType(DataType sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public String getTargetName() {
        return targetName;
    }

    public FieldMapping targetName(String targetName) {
        this.targetName = targetName;
        return this;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetColName() {
        return targetColName;
    }

    public FieldMapping targetColName(String targetColName) {
        this.targetColName = targetColName;
        return this;
    }

    public void setTargetColName(String targetColName) {
        this.targetColName = targetColName;
    }

    public DataType getTargetDataType() {
        return targetDataType;
    }

    public FieldMapping targetDataType(DataType targetDataType) {
        this.targetDataType = targetDataType;
        return this;
    }

    public void setTargetDataType(DataType targetDataType) {
        this.targetDataType = targetDataType;
    }

    public String getTransformation() {
        return transformation;
    }

    public FieldMapping transformation(String transformation) {
        this.transformation = transformation;
        return this;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public Boolean isRequiredData() {
        return requiredData;
    }

    public FieldMapping requiredData(Boolean requiredData) {
        this.requiredData = requiredData;
        return this;
    }

    public void setRequiredData(Boolean requiredData) {
        this.requiredData = requiredData;
    }

    public ContentMapper getContentMapper() {
        return contentMapper;
    }

    public FieldMapping contentMapper(ContentMapper contentMapper) {
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
        if (!(o instanceof FieldMapping)) {
            return false;
        }
        return id != null && id.equals(((FieldMapping) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FieldMapping{" +
            "id=" + getId() +
            ", sourceIndex=" + getSourceIndex() +
            ", sourceName='" + getSourceName() + "'" +
            ", sourceFormat='" + getSourceFormat() + "'" +
            ", sourceStartIndex=" + getSourceStartIndex() +
            ", sourceEndIndex=" + getSourceEndIndex() +
            ", sourceDataType='" + getSourceDataType() + "'" +
            ", targetName='" + getTargetName() + "'" +
            ", targetColName='" + getTargetColName() + "'" +
            ", targetDataType='" + getTargetDataType() + "'" +
            ", transformation='" + getTransformation() + "'" +
            ", requiredData='" + isRequiredData() + "'" +
            "}";
    }
}
