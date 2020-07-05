package com.poseidon.user.domain;

import java.time.OffsetDateTime;
import java.util.StringJoiner;

@SuppressWarnings("unused")
public class UserVO {
    private Long id;
    private String name;
    private String loginId;
    private String password;
    private String role;
    private Boolean expired;
    private OffsetDateTime createdDate;
    private OffsetDateTime modifiedDate;
    private String createdBy;
    private String lastModifiedBy;
    private Boolean startsWith;
    private Boolean includes;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(final Boolean expired) {
        this.expired = expired;
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

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final Boolean startsWith) {
        this.startsWith = startsWith;
    }

    public Boolean getIncludes() {
        return includes;
    }

    public void setIncludes(final Boolean includes) {
        this.includes = includes;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(final String loginId) {
        this.loginId = loginId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserVO.class.getSimpleName() + "[", "]")
                .add("id=" + id).add("name='" + name + "'")
                .add("loginId='" + loginId + "'").add("password='" + password + "'")
                .add("role='" + role + "'").add("createdDate=" + createdDate)
                .add("modifiedDate=" + modifiedDate).add("createdBy='" + createdBy + "'")
                .add("lastModifiedBy='" + lastModifiedBy + "'")
                .add("startsWith=" + startsWith).add("includes=" + includes)
                .toString();
    }
}


