package com.migtation.server.controllers.admin.v2;

import com.migtation.server.dtos.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/v1")
public class AdminControllerV2 {

    @GetMapping("/welcome")
    public ResponseEntity<ApiResponse<Object>> welcome() {
        return ApiResponse.ok("Welcome");
    }
}
