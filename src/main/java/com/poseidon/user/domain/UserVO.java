package com.poseidon.user.domain;

import java.util.StringJoiner;

@SuppressWarnings("unused")
public class UserVO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String companyCode;
    private Boolean expired;

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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(final String companyCode) {
        this.companyCode = companyCode;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(final Boolean expired) {
        this.expired = expired;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserVO.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("email='" + email + "'")
                .add("role='" + role + "'")
                .add("companyCode='" + companyCode + "'")
                .toString();
    }
}


