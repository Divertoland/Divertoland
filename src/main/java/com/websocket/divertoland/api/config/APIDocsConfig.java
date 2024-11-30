package com.websocket.divertoland.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@Profile({"dev", "test"})
@OpenAPIDefinition(info = @Info(title = "Divertoland API", version = "1.0.0"))
public class APIDocsConfig {}