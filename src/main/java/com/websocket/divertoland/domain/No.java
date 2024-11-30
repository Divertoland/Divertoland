package com.websocket.divertoland.domain;

import com.websocket.divertoland.domain.dto.UsuarioDTO;

public class No<T> {

    public UsuarioDTO usuario;
    public No<T> proximo;

    public No(UsuarioDTO usuario) {
        this.usuario = usuario;
        this.proximo = null;
    }
}
