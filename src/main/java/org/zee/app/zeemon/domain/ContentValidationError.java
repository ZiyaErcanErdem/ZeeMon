package org.zee.app.zeemon.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ContentValidationError.
 */
@Entity
@Table(name = "content_validation_error")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentValidationError implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "source_index", nullable = false)
    private Integer sourceIndex;

    @Size(max = 2048)
    @Column(name = "source_text", length = 2048)
    private String sourceText;

    @Size(max = 2048)
    @Column(name = "error_text", length = 2048)
    private String errorText;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contentValidationErrors")
    private Task task;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("contentValidationErrors")
    private TaskExecution taskExecution;

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

    public ContentValidationError sourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
        return this;
    }

    public void setSourceIndex(Integer sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public String getSourceText() {
        return sourceText;
    }

    public ContentValidationError sourceText(String sourceText) {
        this.sourceText = sourceText;
        return this;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getErrorText() {
        return errorText;
    }

    public ContentValidationError errorText(String errorText) {
        this.errorText = errorText;
        return this;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public Task getTask() {
        return task;
    }

    public ContentValidationError task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskExecution getTaskExecution() {
        return taskExecution;
    }

    public ContentValidationError taskExecution(TaskExecution taskExecution) {
        this.taskExecution = taskExecution;
        return this;
    }

    public void setTaskExecution(TaskExecution taskExecution) {
        this.taskExecution = taskExecution;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentValidationError)) {
            return false;
        }
        return id != null && id.equals(((ContentValidationError) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContentValidationError{" +
            "id=" + getId() +
            ", sourceIndex=" + getSourceIndex() +
            ", sourceText='" + getSourceText() + "'" +
            ", errorText='" + getErrorText() + "'" +
            "}";
    }
}
