package com.migration.server.controllers.admin.v1;

import com.migration.server.dtos.request.user.RequestCreateUser;
import com.migration.server.dtos.request.user.RequestUpdateUser;
import com.migration.server.dtos.response.ApiResponse;
import com.migration.server.services.auth.au_service_v1.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/v1/user")
public class UserControllerAdmin {

    private final UserService userService;

    @Autowired
    public UserControllerAdmin(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<Object>> getAll() {
        return null;
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<Object>> getById(@PathVariable UUID id) {
        return null;
    }

    @PostMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<Object>> createUser(
            @Valid @RequestBody RequestCreateUser body
    ) {
        return null;
    }

    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<Object>> edit(
            @PathVariable UUID id,
            @Valid @RequestBody RequestUpdateUser body
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable UUID id) {
        return null;
    }
}
