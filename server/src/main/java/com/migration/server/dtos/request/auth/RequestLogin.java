package com.migration.server.dtos.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestLogin {
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 5, max = 32, message = "Password must be strong!")
//    @Pattern(
//            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
//            message = "Password must contain at least 1 letter and 1 number"
//    )
    private String password;
}
