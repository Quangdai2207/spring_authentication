package com.migration.server.services.auth.au_service_v1.user;

import com.migration.server.entities.UserEntity;
import com.migration.server.exceptions.NotFoundException;
import com.migration.server.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepositories userRepositories;

    @Override
    public UserEntity findByEmail(String email) {
        UserEntity user = userRepositories.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByFirstNameAndLastName(String fName, String lName) {
        return false;
    }

    @Override
    public UserEntity findByPhone(String phone) {
        return userRepositories.findByPhone(phone).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public boolean existsByPhone(String phone) {
        return false;
    }
}
