package com.poseidon.make.domain;

import java.time.OffsetDateTime;
import java.util.StringJoiner;

/**
 * user: Suraj
 * Date: Jun 10, 2012
 * Time: 10:25:12 PM
 */
public class MakeVO {
    private Long id;
    private String makeName;
    private String description;
    private OffsetDateTime createdOn;
    private OffsetDateTime modifiedOn;
    private String createdBy;
    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public OffsetDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(OffsetDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MakeVO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("makeName='" + makeName + "'")
                .add("description='" + description + "'")
                .add("createdOn=" + createdOn)
                .add("modifiedOn=" + modifiedOn)
                .add("createdBy='" + createdBy + "'")
                .add("modifiedBy='" + modifiedBy + "'")
                .toString();
    }
}
