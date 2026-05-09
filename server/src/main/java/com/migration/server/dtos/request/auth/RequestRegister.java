package com.migration.server.dtos.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestRegister {

    @Schema(example = "String")
    @NotBlank(message = "First name is required")
    @Pattern(
            regexp = "^[a-zA-Z]+$",
            message = "First name must contain only letters"
    )
    private String firstName;

    @Schema(example = "String")
    @NotBlank(message = "Last name is required")
    @Pattern(
            regexp = "^[a-zA-Z]+$",
            message = "Last name must contain only letters"
    )
    private String lastName;

    @Schema(example = "String")
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @Schema(example = "String")
    @NotBlank(message = "Phone is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone must be exactly 10 digits"
    )
    private String phone;

    @Schema(example = "String")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain uppercase, lowercase and number"
    )
    private String password;

    @Schema(example = "String")
    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}