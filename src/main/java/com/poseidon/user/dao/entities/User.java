package com.poseidon.user.dao.entities;


import com.poseidon.init.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "member")
@SuppressWarnings("PMD.DataClass")
public class User extends CommonEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "companyCode")
    private String companyCode;

    @Column(name = "photo")
    private String photo;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean expired) {
        this.enabled = expired;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(final String companyCode) {
        this.companyCode = companyCode;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(final Role role) {
        this.roles.add(role);
    }

    @Transient
    public String getPhotosImagePath() {
        if (this.getId() == null || this.getPhoto() == null) {
            return "/img/default-user-photo.svg";
        }
        return "/user-photos/" + this.getId() + "/" + this.getPhoto();
    }
}
