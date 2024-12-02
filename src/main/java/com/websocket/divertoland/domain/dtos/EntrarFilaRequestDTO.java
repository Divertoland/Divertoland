package com.websocket.divertoland.domain.dtos;

import lombok.Data;

@Data
public class EntrarFilaRequestDTO {
        private UsuarioDTO usuario;
        private Long atracaoId;
}
