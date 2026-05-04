package com.migtation.server.configs.seccurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class ObjectMapperDefine implements WebMvcConfigurer {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
