package com.poseidon.user.domain;

import java.time.OffsetDateTime;

/**
 * @author : Suraj Muraleedharan
 * Date: Nov 27, 2010
 * Time: 12:28:55 PM
 */
@SuppressWarnings("unused")
public class UserVO {
    private Long id;
    private String name;
    private String loginId;
    private String password;
    private String role;
    private OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private Boolean startsWith;
    private Boolean includes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public OffsetDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(OffsetDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(Boolean startsWith) {
        this.startsWith = startsWith;
    }

    public Boolean getIncludes() {
        return includes;
    }

    public void setIncludes(Boolean includes) {
        this.includes = includes;
    }

    @Override
    public String toString() {
        return "UserVO{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", loginId='"
                + loginId
                + '\''
                + ", password='"
                + password
                + '\''
                + ", role='"
                + role
                + '\''
                + ", createdDate="
                + createdDate
                + ", modifiedDate="
                + modifiedDate
                + ", createdBy='"
                + createdBy
                + '\''
                + ", lastModifiedBy='"
                + lastModifiedBy
                + '\''
                + '}';
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}


