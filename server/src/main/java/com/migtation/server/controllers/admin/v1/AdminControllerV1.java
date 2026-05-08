package com.migtation.server.controllers.admin.v1;

import com.migtation.server.dtos.response.ApiResponse;
import com.migtation.server.services.admin.service_v1.admin_user.AdminUserService_V1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/v1")
public class AdminControllerV1 {

    private AdminUserService_V1 adminUserService_V1;

    @Autowired
    public AdminControllerV1(AdminUserService_V1 adminUserService_V1) {
        this.adminUserService_V1 = adminUserService_V1;
    }

    @GetMapping("/welcome")
    public ResponseEntity<ApiResponse<Object>> welcome() {
        return ApiResponse.ok("Welcome");
    }
}
