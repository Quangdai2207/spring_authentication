package com.migration.server.configs.seccurity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationProviderCustom implements WebMvcConfigurer {
    private final UserDetailsCustom UserDetailsCustom;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationProviderCustom(
            UserDetailsCustom UserDetailsCustom,
            PasswordEncoder passwordEncoder
    ) {
        this.UserDetailsCustom = UserDetailsCustom;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(UserDetailsCustom);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
}
