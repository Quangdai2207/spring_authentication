package com.migration.server.services.auth.au_service_v1;

import com.migration.server.configs.seccurity.jwt.JwtProvider;
import com.migration.server.dtos.request.auth.RequestLogin;
import com.migration.server.dtos.request.auth.RequestRegister;
import com.migration.server.dtos.response.ApiResponse;
import com.migration.server.dtos.response.auth.AuthData;
import com.migration.server.dtos.response.user.ResponseUserDetail;
import com.migration.server.entities.Role;
import com.migration.server.entities.UserEntity;
import com.migration.server.entities.UserRole;
import com.migration.server.exceptions.BadRequestException;
import com.migration.server.mappers.UserMapper;
import com.migration.server.repositories.RoleRepositories;
import com.migration.server.repositories.UserRepositories;
import com.migration.server.repositories.UserRoleRepositories;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepositories userRepositories;
    private final UserRoleRepositories userRoleRepositories;
    private final RoleRepositories roleRepositories;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            JwtProvider jwtProvider,
            UserRepositories userRepositories,
            UserRoleRepositories userRoleRepositories,
            RoleRepositories roleRepositories,
            PasswordEncoder encoder
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userRepositories = userRepositories;
        this.userRoleRepositories = userRoleRepositories;
        this.roleRepositories = roleRepositories;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<ApiResponse<AuthData>> login(RequestLogin body, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword())
        );
        System.out.println("Loi tai Class: " + authentication.getPrincipal().toString());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtProvider.generateToken(authentication);
        Cookie cookie = new Cookie("access_token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        cookie.setSecure(true); // Khong can set cookie len localhost same origin server
        response.addCookie(cookie);

        return ApiResponse.ok(AuthData.builder().email(userDetails.getUsername()).build(), "Login Successful");
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse<ResponseUserDetail>> register(RequestRegister body) {

        boolean isExist = userRepositories.existsByEmailOrPhone(body.getEmail(), body.getPhone());
        if (isExist) throw new BadRequestException("Email or phone already exists");

        if (!body.getPassword().equals(body.getConfirmPassword()))
            throw new BadRequestException("Passwords do not match");

        UserEntity user = UserMapper.registerToUserEntity(body);
        user.setPassword(encoder.encode(body.getPassword()));
        user = userRepositories.save(user);

        Role role = roleRepositories.findById(3).get();

        int count = userRoleRepositories.findAll().size();
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();

        userRole.setId(count++);
        userRoleRepositories.save(userRole);

        return ApiResponse.created(ResponseUserDetail.builder()
                .firsName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build(), "Register Successful");
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setSecure(true);

        response.addCookie(cookie);

        return ApiResponse.ok("Logout Successful");

    }

    @Override
    public ResponseEntity<ApiResponse<ResponseUserDetail>> profile() {
        return null;
    }
}
