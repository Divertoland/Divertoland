package com.websocket.divertoland.domain.dto;

import lombok.Data;

@Data
public class EntrarFilaRequestDTO {
        private UsuarioDTO usuario;
        private Long atracaoId;
}
