package com.example.star_wars_project.model.entity;

import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "genres")
public class Genre extends BaseEntity {
    private GenreNameEnum name;

    public Genre() {
    }

    @Enumerated(EnumType.STRING)
    public GenreNameEnum getName() {
        return name;
    }

    public void setName(GenreNameEnum name) {
        this.name = name;
    }
}