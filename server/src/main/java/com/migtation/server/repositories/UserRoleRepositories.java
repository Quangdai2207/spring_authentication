package com.migtation.server.repositories;

import com.migtation.server.entities.Role;
import com.migtation.server.entities.UserEntity;
import com.migtation.server.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRoleRepositories extends JpaRepository<UserRole, Integer> {
    Set<UserRole> findByUser(UserEntity user);

    List<UserRole> findByRole(Role role);
}
