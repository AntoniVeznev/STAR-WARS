package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {


    Role findRoleByName(String user);


}
