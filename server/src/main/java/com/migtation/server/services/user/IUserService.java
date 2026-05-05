package com.migtation.server.services.user;

import com.migtation.server.entities.UserEntity;

import java.util.Optional;

public interface IUserService {
    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByFirstNameAndLastName(String fName, String lName);

    UserEntity findByPhone(String phone);

    boolean existsByPhone(String phone);
}
