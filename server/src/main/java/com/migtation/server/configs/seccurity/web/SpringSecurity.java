package com.migtation.server.configs.seccurity.web;

import com.migtation.server.configs.seccurity.handles_entry_point_custom.HandleAuthenticationAccessDenied;
import com.migtation.server.configs.seccurity.handles_entry_point_custom.HandleUnAuthentication;
import com.migtation.server.configs.seccurity.jwt.JwtFilterChains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurity implements WebMvcConfigurer {
    private final CorsConfigOrigin corsConfigOrigin;
    private final HandleUnAuthentication handleUnAuthentication;
    private final HandleAuthenticationAccessDenied handleAuthenticationAccessDenied;
    private final AuthenticationProviderCustom authenticationProviderCustom;
    private final JwtFilterChains jwtFilterChains;

    @Autowired
    public SpringSecurity(
            CorsConfigOrigin corsConfigOrigin,
            HandleUnAuthentication handleUnAuthentication,
            HandleAuthenticationAccessDenied handleAuthenticationAccessDenied,
            AuthenticationProviderCustom authenticationProviderCustom,
            JwtFilterChains jwtFilterChain
    ) {
        this.corsConfigOrigin = corsConfigOrigin;
        this.handleUnAuthentication = handleUnAuthentication;
        this.handleAuthenticationAccessDenied = handleAuthenticationAccessDenied;
        this.authenticationProviderCustom = authenticationProviderCustom;
        this.jwtFilterChains = jwtFilterChain;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigOrigin.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                                    "/index.html",
                                    "/assets/**",
                                    "/*.js",
                                    "/*.css",
                                    "/favicon.ico",
                                    "/api/public/**",
                                    "/api/v1/auth/**",
                                    "/ws/**",
                                    "/app/**",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**"
                            ).permitAll()
                            .requestMatchers("/api/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                            .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                            .requestMatchers("/api/super-admin/**").hasAnyRole("SUPER_ADMIN")
                            .anyRequest().authenticated();
                })
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(handleUnAuthentication)
                        .accessDeniedHandler(handleAuthenticationAccessDenied)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilterChains, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(authenticationProviderCustom.authenticationProvider());
        http.headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("default-src 'self'")
                )
        );

        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
