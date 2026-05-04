package com.migtation.server.configs.seccurity.handles_entry_point_custom;

import com.migtation.server.dtos.response.auth.AuthStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class HandleUnAuthentication implements AuthenticationEntryPoint {
    private ObjectMapper objectMapper;

    @Autowired
    public HandleUnAuthentication(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /// Tuy chinh Response entry-point thay cho response mac dinh cua spring Security
        AuthStatus<String> authStatus = AuthStatus.<String>builder()
                .success(false)
                .status(401)
                .message("Authentication required")
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(authStatus));
    }
}
