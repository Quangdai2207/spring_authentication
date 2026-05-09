package com.migration.server.repositories;

import com.migration.server.entities.Role;
import com.migration.server.entities.UserEntity;
import com.migration.server.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepositories extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUser(UserEntity user);

    List<UserRole> findByRole(Role role);
}
