package com.migration.server.mappers;

import com.migration.server.dtos.request.auth.RequestRegister;
import com.migration.server.dtos.response.user.ResponseUserDetail;
import com.migration.server.entities.UserEntity;

public class UserMapper {

    public static UserEntity registerToUserEntity(RequestRegister body) {
        return UserEntity.builder()
                .firstName(body.getFirstName())
                .lastName(body.getLastName())
                .email(body.getEmail())
                .phone(body.getPhone())
                .password(body.getPassword())
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
