package com.migtation.server.configs.seccurity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationProviderCustom implements WebMvcConfigurer {
    private final UserDetailsCustom UserDetailsCustom;
    private final PasswordConfig passwordConfig;

    @Autowired
    public AuthenticationProviderCustom(
            UserDetailsCustom UserDetailsCustom,
            PasswordConfig passwordConfig
    ) {
        this.UserDetailsCustom = UserDetailsCustom;
        this.passwordConfig = passwordConfig;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(UserDetailsCustom);
        provider.setPasswordEncoder(passwordConfig.bCryptPasswordEncoder());

        return provider;
    }
}
