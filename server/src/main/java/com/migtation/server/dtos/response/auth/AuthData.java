package com.migtation.server.dtos.response.auth;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthData {
    private String email;
}
