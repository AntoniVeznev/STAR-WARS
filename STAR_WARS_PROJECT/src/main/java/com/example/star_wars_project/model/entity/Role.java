package com.example.star_wars_project.model.entity;

import com.example.star_wars_project.model.entity.enums.RoleNameEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    private RoleNameEnum name;

    public Role() {
    }

    @Enumerated(EnumType.STRING)
    public RoleNameEnum getName() {
        return name;
    }

    public void setName(RoleNameEnum name) {
        this.name = name;
    }
}