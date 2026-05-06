package com.migtation.server.services.auth.au_service_v1.user;

import com.migtation.server.entities.UserEntity;

public interface IUserService {
    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByFirstNameAndLastName(String fName, String lName);

    UserEntity findByPhone(String phone);

    boolean existsByPhone(String phone);
}
