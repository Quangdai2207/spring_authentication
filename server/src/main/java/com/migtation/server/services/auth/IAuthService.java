package com.migtation.server.services.auth;

import com.migtation.server.dtos.request.auth.RequestLogin;
import com.migtation.server.dtos.request.auth.RequestRegister;
import com.migtation.server.dtos.response.ApiResponse;
import com.migtation.server.dtos.response.auth.AuthData;
import com.migtation.server.dtos.response.user.ResponseUserDetail;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<ApiResponse<AuthData>> login(RequestLogin body, HttpServletResponse response);

    ResponseEntity<ApiResponse<ResponseUserDetail>> register(RequestRegister body);

    ResponseEntity<ApiResponse<Object>> logout(HttpServletResponse response);

    ResponseEntity<ApiResponse<ResponseUserDetail>> profile();
}
