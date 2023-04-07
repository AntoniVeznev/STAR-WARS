package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.Platform;
import com.example.star_wars_project.model.entity.enums.PlatformNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Platform findPlatformByName(PlatformNameEnum platforms);
}