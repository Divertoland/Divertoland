package com.websocket.divertoland.domain.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nome;
    private AtracaoDTO atracao;
}
