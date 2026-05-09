package com.migration.server.dtos.response.auth;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthStatus<T> {
    private int status;
    private boolean success;
    private T message;
}
