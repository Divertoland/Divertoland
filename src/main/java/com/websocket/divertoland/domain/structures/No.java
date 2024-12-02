package com.websocket.divertoland.domain.structures;

public class No<T> {

    public No<T> proximo;
    public T value;

    public No(T dado) {
        this.value = dado;
        this.proximo = null;
    }
}
