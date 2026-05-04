package com.migtation.server.configs.seccurity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationProviderCustom implements WebMvcConfigurer {
    private final UserDetailsCustom UserDetailsCustom;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthenticationProviderCustom(
            UserDetailsCustom UserDetailsCustom,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        this.UserDetailsCustom = UserDetailsCustom;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(UserDetailsCustom);
        provider.setPasswordEncoder(bCryptPasswordEncoder);

        return provider;
    }
}
