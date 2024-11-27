package com.websocket.divertoland.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.websocket.divertoland.services.UserServiceV1;
import com.websocket.divertoland.services.abstractions.UserService;

@Configuration
public class DependencyConfig 
{
    @Bean
    @Primary
    public UserService UserService(){ return new UserServiceV1(); }
}
