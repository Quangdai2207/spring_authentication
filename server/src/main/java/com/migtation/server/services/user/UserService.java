package com.migtation.server.services.user;

import com.migtation.server.entities.UserEntity;
import com.migtation.server.exceptions.NotFoundException;
import com.migtation.server.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepositories userRepositories;

    @Override
    public UserEntity findByEmail(String email) {
        return userRepositories.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
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
