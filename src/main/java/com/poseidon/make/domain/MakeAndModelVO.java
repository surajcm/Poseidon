package com.poseidon.make.domain;

import java.time.OffsetDateTime;
import java.util.StringJoiner;

/**
 * user: Suraj.
 * Date: Jun 2, 2012
 * Time: 7:26:27 PM
 */
public class MakeAndModelVO {
    private Long id;
    private Long makeId;
    private Long modelId;
    private String makeName;
    private String modelName;
    private String description;
    private Boolean startswith;
    private Boolean includes;
    private OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getMakeId() {
        return makeId;
    }

    public void setMakeId(final Long makeId) {
        this.makeId = makeId;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(final String makeName) {
        this.makeName = makeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(final Long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    public Boolean getStartswith() {
        return startswith;
    }

    public void setStartswith(final Boolean startswith) {
        this.startswith = startswith;
    }

    public Boolean getIncludes() {
        return includes;
    }

    public void setIncludes(final Boolean includes) {
        this.includes = includes;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final OffsetDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MakeAndModelVO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("makeId=" + makeId)
                .add("modelId=" + modelId)
                .add("makeName='" + makeName + "'")
                .add("modelName='" + modelName + "'")
                .add("description='" + description + "'")
                .add("startswith=" + startswith)
                .add("includes=" + includes)
                .add("createdDate=" + createdDate)
                .add("modifiedDate=" + modifiedDate)
                .add("createdBy='" + createdBy + "'")
                .add("modifiedBy='" + modifiedBy + "'")
                .toString();
    }
}
