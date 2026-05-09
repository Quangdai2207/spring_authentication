package com.migration.server.controllers.auth;

import com.migration.server.dtos.request.auth.RequestLogin;
import com.migration.server.dtos.request.auth.RequestRegister;
import com.migration.server.dtos.response.ApiResponse;
import com.migration.server.dtos.response.auth.AuthData;
import com.migration.server.dtos.response.user.ResponseUserDetail;
import com.migration.server.services.auth.au_service_v1.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerV1 {
    private final AuthService authService;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthControllerV1(AuthService authService, PasswordEncoder encoder) {
        this.authService = authService;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthData>> login(
            @RequestBody @Valid RequestLogin body,
            HttpServletResponse response
    ) {
        return authService.login(body, response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ResponseUserDetail>> register(
            @Valid @RequestBody RequestRegister body
    ) {
        return authService.register(body);
    }

    @GetMapping("/authenticated")
    public ResponseEntity<ApiResponse<Object>> authenticated(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ApiResponse.unauthorize();
        }
        return ApiResponse.ok();
    }

    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(HttpServletResponse response) {
        return authService.logout(response);
    }
}
