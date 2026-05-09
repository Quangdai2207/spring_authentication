package com.migration.server.services.auth.au_service_v1;

import com.migration.server.dtos.request.auth.RequestLogin;
import com.migration.server.dtos.request.auth.RequestRegister;
import com.migration.server.dtos.response.ApiResponse;
import com.migration.server.dtos.response.auth.AuthData;
import com.migration.server.dtos.response.user.ResponseUserDetail;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<ApiResponse<AuthData>> login(RequestLogin body, HttpServletResponse response);

    ResponseEntity<ApiResponse<ResponseUserDetail>> register(RequestRegister body);

    ResponseEntity<ApiResponse<Object>> logout(HttpServletResponse response);

    ResponseEntity<ApiResponse<ResponseUserDetail>> profile();
}
