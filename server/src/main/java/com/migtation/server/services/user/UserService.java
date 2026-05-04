package com.migtation.server.services.user;

import com.migtation.server.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return Optional.empty();
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
    public Optional<UserEntity> findByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public boolean existsByPhone(String phone) {
        return false;
    }
}
