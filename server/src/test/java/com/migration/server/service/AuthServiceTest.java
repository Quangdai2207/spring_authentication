package com.migration.server.service;


import com.migration.server.configs.seccurity.jwt.JwtProvider;
import com.migration.server.dtos.request.auth.RequestLogin;
import com.migration.server.dtos.response.ApiResponse;
import com.migration.server.dtos.response.auth.AuthData;
import com.migration.server.entities.UserEntity;
import com.migration.server.services.auth.au_service_v1.AuthService;
import com.migration.server.services.auth.au_service_v1.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/// Khai bao chay Unit test tren moi truong DEV hoac PRODUCT
@ActiveProfiles("dev")
@SpringBootTest
public class AuthServiceTest {
    @Autowired
    @InjectMocks
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;


    private RequestLogin request;

    @BeforeEach
    void setup() {
        request = new RequestLogin();
        request.setEmail("dai@gmail.com");
        request.setPassword("12345");
    }

    @Test
    void login_success() {

        User userDetails = new User(
                "dai@gmail.com",
                "12345",
                Collections.emptyList()
        );

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        when(authentication.getPrincipal())
                .thenReturn(userDetails);

        when(jwtProvider.generateToken(authentication))
                .thenReturn("jwt-token-demo");

        ResponseEntity<ApiResponse<AuthData>> result =
                authService.login(request, response);

        // verify authenticate called
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // verify add cookie
        ArgumentCaptor<Cookie> cookieCaptor =
                ArgumentCaptor.forClass(Cookie.class);

        verify(response).addCookie(cookieCaptor.capture());

        Cookie cookie = cookieCaptor.getValue();

        assertEquals("access_token", cookie.getName());
        assertEquals("jwt-token-demo", cookie.getValue());

        // verify response
        assertNotNull(result);

        ApiResponse<AuthData> body = result.getBody();

        assertNotNull(body);
        assertEquals("Login Successful", body.getMessage());

        assertEquals(
                "dai@gmail.com",
                body.getData().getEmail()
        );
    }

    /// Test without param
    @Test
    public void findByEmailTest() {
        UserEntity user = userService.findByEmail("dai@gmail.com");
        assertNotNull(user);
        assertEquals(10, 5 * 2);
    }

    /// Test with param, pass value by @CsvSource
    @ParameterizedTest
    @ValueSource(strings = {
            "khang@gmail.com",
            "huan@gmail.com",
            "dai@gmail.com"
    })
    public void findByEmailTest_withCSVSourcce(String email) {
        assertNotNull(userService.findByEmail(email));
    }

    /// Test with param, pass value by @ValueSource
    @ParameterizedTest
    @CsvSource({
            "khang@gmail.com",
            "huan@gmail.com",
            "dai@gmail.com"
    })
    public void findByEmailTest_withValueSource(String email) {
        assertNotNull(userService.findByEmail(email));
    }
}
