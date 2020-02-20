package org.zee.app.zeemon.extensions.execution.model.dto;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.zee.app.zeemon.domain.enumeration.DataType;

public class FieldMappingDto {

    private Long id;

    @Min(value = 0)
    private Integer sourceIndex;

    @Size(max = 255)
    private String sourceName;

    @Size(min = 0, max = 255)
    private String sourceFormat;

    @Min(value = 0)
    private Integer sourceStartIndex;

    @Min(value = 0)
    private Integer sourceEndIndex;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DataType sourceDataType;

    @NotNull
    @Size(max = 255)
    private String targetName;

    @NotNull
    @Size(max = 255)
    private String targetColName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DataType targetDataType;

    @Size(max = 2048)
    private String transformation;

    private Boolean requiredData;

    private Long contentMapperId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSourceIndex() {
        return sourceIndex;
    }

    public FieldMappingDto sourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
        return this;
    }

    public void setSourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public String getSourceName() {
        return sourceName;
    }

    public FieldMappingDto sourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceFormat() {
        return sourceFormat;
    }

    public FieldMappingDto sourceFormat(String sourceFormat) {
        this.sourceFormat = sourceFormat;
        return this;
    }

    public void setSourceFormat(String sourceFormat) {
        this.sourceFormat = sourceFormat;
    }

    public Integer getSourceStartIndex() {
        return sourceStartIndex;
    }

    public FieldMappingDto sourceStartIndex(Integer sourceStartIndex) {
        this.sourceStartIndex = sourceStartIndex;
        return this;
    }

    public void setSourceStartIndex(Integer sourceStartIndex) {
        this.sourceStartIndex = sourceStartIndex;
    }

    public Integer getSourceEndIndex() {
        return sourceEndIndex;
    }

    public FieldMappingDto sourceEndIndex(Integer sourceEndIndex) {
        this.sourceEndIndex = sourceEndIndex;
        return this;
    }

    public void setSourceEndIndex(Integer sourceEndIndex) {
        this.sourceEndIndex = sourceEndIndex;
    }

    public DataType getSourceDataType() {
        return sourceDataType;
    }

    public FieldMappingDto sourceDataType(DataType sourceDataType) {
        this.sourceDataType = sourceDataType;
        return this;
    }

    public void setSourceDataType(DataType sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public String getTargetName() {
        return targetName;
    }

    public FieldMappingDto targetName(String targetName) {
        this.targetName = targetName;
        return this;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetColName() {
        return targetColName;
    }

    public FieldMappingDto targetColName(String targetColName) {
        this.targetColName = targetColName;
        return this;
    }

    public void setTargetColName(String targetColName) {
        this.targetColName = targetColName;
    }

    public DataType getTargetDataType() {
        return targetDataType;
    }

    public FieldMappingDto targetDataType(DataType targetDataType) {
        this.targetDataType = targetDataType;
        return this;
    }

    public void setTargetDataType(DataType targetDataType) {
        this.targetDataType = targetDataType;
    }

    public String getTransformation() {
        return transformation;
    }

    public FieldMappingDto transformation(String transformation) {
        this.transformation = transformation;
        return this;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public Boolean isRequiredData() {
        return requiredData;
    }
    
	public Boolean getRequiredData() {
		return requiredData;
	}

    public FieldMappingDto requiredData(Boolean requiredData) {
        this.requiredData = requiredData;
        return this;
    }

    public void setRequiredData(Boolean requiredData) {
        this.requiredData = requiredData;
    }
        

    public Long getContentMapperId() {
		return contentMapperId;
	}

	public void setContentMapperId(Long contentMapperId) {
		this.contentMapperId = contentMapperId;
	}


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldMappingDto)) {
            return false;
        }
        return id != null && id.equals(((FieldMappingDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FieldMapping{" +
            "id=" + getId() +
            ", contentMapperId=" + getContentMapperId() +
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
