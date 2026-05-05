package com.migtation.server.controllers.auth;

import com.migtation.server.configs.seccurity.ApplicationProperties;
import com.migtation.server.dtos.response.ApiResponse;
import com.migtation.server.dtos.response.auth.AuthData;
import com.migtation.server.dtos.response.auth.AuthStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerV1 {

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthData>> login(
            @Param("email") String email,
            @Param("password") String password
    ) {
        System.out.println("Username: " + email);
        System.out.println("Password: " + password);

        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthData>> register(
            @Param("firstName") String fName,
            @Param("lastName") String lName,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("password") String password,
            @Param("confirmPassword") String confirmPassword
    ) {
        System.out.println("email: " + email);
        System.out.println("fName: " + fName);
        System.out.println("lName: " + lName);
        System.out.println("Phone: " + phone);
        System.out.println("Password: " + password);
        System.out.println("Confirm Password: " + confirmPassword);

        return null;
    }

    @GetMapping("/authenticated")
    public ResponseEntity<ApiResponse<Object>> authenticated(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ApiResponse.unauthorize();
        }
        return ApiResponse.unauthorize(AuthStatus.builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .message(HttpStatus.OK.getReasonPhrase())
                .build());
    }
}
