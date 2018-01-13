package com.poseidon.make.domain;

import java.util.Date;

/**
 * user: Suraj
 * Date: Jun 10, 2012
 * Time: 10:25:12 PM
 */
public class MakeVO {
    private Long id;
    private String makeName;
    private String description;
    private Date createdOn;
    private Date modifiedOn;
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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
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
        return "MakeVO{" +
                "id=" + id +
                ", makeName='" + makeName + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
