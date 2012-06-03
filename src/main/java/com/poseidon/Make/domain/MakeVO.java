package com.poseidon.Make.domain;

/**
 * User: Suraj
 * Date: Jun 2, 2012
 * Time: 7:26:27 PM
 */
public class MakeVO {
    private Long makeId;
    private Long modelId;
    private String makeName;
    private String modelName;
    private String description;
    private Boolean startswith;
    private Boolean includes;

    public Long getMakeId() {
        return makeId;
    }

    public void setMakeId(Long makeId) {
        this.makeId = makeId;
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

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Boolean getStartswith() {
        return startswith;
    }

    public void setStartswith(Boolean startswith) {
        this.startswith = startswith;
    }

    public Boolean getIncludes() {
        return includes;
    }

    public void setIncludes(Boolean includes) {
        this.includes = includes;
    }

    @Override
    public String toString() {
        return "MakeVO{" +
                "makeId=" + makeId +
                ", modelId=" + modelId +
                ", makeName='" + makeName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
