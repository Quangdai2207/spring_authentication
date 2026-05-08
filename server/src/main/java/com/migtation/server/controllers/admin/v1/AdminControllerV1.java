package com.migtation.server.controllers.admin.v1;

import com.migtation.server.dtos.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/v1")
public class AdminControllerV1 {

    @GetMapping("/welcome")
    public ResponseEntity<ApiResponse<Object>> welcome() {

        return ApiResponse.ok("Welcome");
    }
}
