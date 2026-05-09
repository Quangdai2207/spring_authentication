package com.migration.server.repositories;

import com.migration.server.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositories extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
