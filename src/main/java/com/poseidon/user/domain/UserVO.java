package com.poseidon.user.domain;

import com.poseidon.user.dao.entities.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@SuppressWarnings("unused")
public class UserVO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
    private String companyCode;
    private Boolean enabled;

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

    /*public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }*/

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(final Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(final String companyCode) {
        this.companyCode = companyCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
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
                .add("role='" + roles + "'")
                .add("companyCode='" + companyCode + "'")
                .toString();
    }
}


