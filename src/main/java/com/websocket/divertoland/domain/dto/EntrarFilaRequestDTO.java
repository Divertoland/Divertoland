package com.websocket.divertoland.domain.dto;

import com.websocket.divertoland.domain.Usuario;
import lombok.Data;

@Data
public class EntrarFilaRequestDTO {
        private Usuario usuario;
        private Long atracaoId;
}
