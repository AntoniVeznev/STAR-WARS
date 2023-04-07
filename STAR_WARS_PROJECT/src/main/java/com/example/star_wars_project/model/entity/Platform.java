package com.example.star_wars_project.model.entity;

import com.example.star_wars_project.model.entity.enums.PlatformNameEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "platforms")
public class Platform extends BaseEntity {
    private PlatformNameEnum name;

    public Platform() {
    }

    @Enumerated(EnumType.STRING)
    public PlatformNameEnum getName() {
        return name;
    }

    public void setName(PlatformNameEnum name) {
        this.name = name;
    }
}