package com.poseidon.make.dao.entities;

import com.poseidon.init.entity.CommonEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "make")
@SuppressWarnings("PMD.DataClass")
public class Make extends CommonEntity {

    @Column(name = "makeName")
    private String makeName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "make")
    private List<Model> models;

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

    public List<Model> getModels() {
        return models;
    }

    public void setModels(final List<Model> models) {
        this.models = models;
    }

}
