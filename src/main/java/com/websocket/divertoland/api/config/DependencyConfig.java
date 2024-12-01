package com.websocket.divertoland.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.websocket.divertoland.services.abstractions.AtracaoService;
import com.websocket.divertoland.services.abstractions.UsuarioService;
import com.websocket.divertoland.services.v1.AtracaoServiceV1;
import com.websocket.divertoland.services.v1.UsuarioServiceV1;

@Configuration
public class DependencyConfig 
{
    @Bean
    @Primary
    public AtracaoService AtracaoService(){ return new AtracaoServiceV1(); }
    
    @Bean
    @Primary
    public UsuarioService UsuarioService(){ return new UsuarioServiceV1(); }
}
