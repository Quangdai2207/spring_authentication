package com.migration.server.repositories;

import com.migration.server.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositories extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Optional<UserEntity> findByPhone(String phone);

    boolean existsByPhone(String phone);
    boolean existsByEmailOrPhone(String email, String phone);
}
