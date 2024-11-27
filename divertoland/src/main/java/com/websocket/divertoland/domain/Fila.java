package com.websocket.divertoland.domain;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;




public class Fila<T> {
    private No<T> inicio, fim;
    private int tamanho;
    @ManyToOne
    @JoinColumn(name = "brinquedo_id", referencedColumnName = "id", nullable = false)
    private Brinquedo brinquedo;

    public No<T> getInicio() {
        return inicio;
    }

    public void setInicio(No<T> inicio) {
        this.inicio = inicio;
    }

    public No<T> getFim() {
        return fim;
    }

    public void setFim(No<T> fim) {
        this.fim = fim;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Brinquedo getBrinquedo() {
        return brinquedo;
    }

    public void setBrinquedo(Brinquedo brinquedo) {
        this.brinquedo = brinquedo;
    }

    public Fila() {
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    public boolean isEmpty() {
        return (inicio == null);
    }
    public int tamanhoFila() {
        return tamanho;
    }

    public void enqueue(Usuario usuario)
    {
        No<T> novoNo = new No<>(usuario);
        if (isEmpty()) {
            inicio = novoNo;
            fim = inicio;
        } else {
            inicio = novoNo;
            fim = novoNo;
            fim.proximo = novoNo;

        }
        tamanho++;
    }

    public Usuario dequeue() {
        if (isEmpty()){
            return null;
            }
        No<T> temp = inicio;
        inicio = inicio.proximo;
        if(inicio==null) {
            fim = null;
        }
        tamanho--;
        return temp.usuario;
    }

    public int size() {
        return tamanho;
    }

}
