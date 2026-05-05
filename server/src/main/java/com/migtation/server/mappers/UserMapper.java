package com.migtation.server.mappers;

import com.migtation.server.dtos.request.auth.RequestRegister;
import com.migtation.server.dtos.response.user.ResponseUserDetail;
import com.migtation.server.entities.UserEntity;

import java.util.HashSet;

public class UserMapper {

    public static UserEntity registerToUserEntity(RequestRegister body) {
        return UserEntity.builder()
                .firstName(body.getFirstName())
                .lastName(body.getLastName())
                .email(body.getEmail())
                .phone(body.getPhone())
                .password(body.getPassword())
                .userRoles(new HashSet<>())
                .build();
    }

    public static ResponseUserDetail registerToUserEntity(UserEntity user) {
        return ResponseUserDetail.builder()
                .firsName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
