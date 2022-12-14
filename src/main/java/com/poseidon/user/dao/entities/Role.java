package com.poseidon.user.dao.entities;

import com.poseidon.init.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends CommonEntity  {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public String getName() {
        return name;
    }

    public Role() {
    }

    public Role(final Long id) {
        this.setId(id);
    }

    public Role(final Long id, final String name) {
        this.setId(id);
        this.setName(name);
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
