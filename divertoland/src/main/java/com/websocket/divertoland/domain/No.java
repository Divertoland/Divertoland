package com.websocket.divertoland.domain;

public class No<T> {

    public Usuario usuario;
    public No<T> proximo;

    public No(Usuario usuario) {
        this.usuario = usuario;
        this.proximo = null;
    }
}
