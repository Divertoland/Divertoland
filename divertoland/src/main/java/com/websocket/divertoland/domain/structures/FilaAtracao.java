package com.websocket.divertoland.domain.structures;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilaAtracao {

    @Bean
    public HashMap<Long,Fila> hashAtracao(){
        return new HashMap<>();
    }
}
