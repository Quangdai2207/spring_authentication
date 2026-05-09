package com.migration.server.services.auth.au_service_v1.user;

import com.migration.server.entities.UserEntity;

public interface IUserService {
    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByFirstNameAndLastName(String fName, String lName);

    UserEntity findByPhone(String phone);

    boolean existsByPhone(String phone);
}
