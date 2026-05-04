package com.migtation.server.dtos.response.user;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseUserDetail<T> {
    private String email;
    private String phone;
    private String firsName;
    private String lastName;
    private T relateData;
}
