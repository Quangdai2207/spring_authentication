package com.migtation.server.controllers.publics;

import com.migtation.server.dtos.request.BcryptGenerator;
import com.migtation.server.dtos.response.ApiResponse;
import com.migtation.server.services.auth.au_service_v1.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/public")
public class PublicController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public PublicController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    // Tao hashCode Bcrypt from password
    @PostMapping("/bcrypt-generator")
    public ResponseEntity<ApiResponse<Object>> bcryptGenerator(@RequestBody BcryptGenerator body) {
        body.setHashCode(passwordEncoder.encode(body.getPassword()));
        return ApiResponse.ok(body, "OK");
    }

    // Validate password matches hashcode
    @PostMapping("/verify-hash")
    public ResponseEntity<ApiResponse<Object>> verifyHash(
            @RequestBody BcryptGenerator body
    ) {
        return ApiResponse.ok(
                passwordEncoder.matches(body.getPassword(), body.getHashCode()),
                "OK"
        );
    }
}
