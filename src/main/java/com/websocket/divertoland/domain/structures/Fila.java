package com.websocket.divertoland.domain.structures;


import com.websocket.divertoland.domain.No;
import com.websocket.divertoland.domain.dto.ListarFilaDTO;
import com.websocket.divertoland.domain.dto.UsuarioDTO;

import java.util.ArrayList;
import java.util.List;


public class Fila<T> {
    private No<T> inicio, fim;
    private int tamanho;

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

    public void enqueue(UsuarioDTO usuario)
    {
        No<T> novoNo = new No<>(usuario);
        if (isEmpty()) {
            inicio = novoNo;
        } else {
            fim.proximo = novoNo;
        }
        fim = novoNo;
        tamanho++;
    }

    public UsuarioDTO dequeue() {
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

    public int posicaoUsuario(UsuarioDTO usuario) {
        No<T> atual = inicio; // Começa pelo início da fila
        int posicao = 0;      // Contador para a posição

        while (atual != null) {
            if (atual.usuario.equals(usuario)) {
                return posicao; // Retorna a posição do usuário se encontrado
            }
            atual = atual.proximo; // Move para o próximo nó
            posicao++;
        }

        return -1; // Retorna -1 se o usuário não estiver na fila
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("A fila está vazia.");
            return;
        }

        No<T> atual = inicio;
        System.out.print("Fila: ");
        while (atual != null) {
            System.out.print(atual.usuario + " ");
            atual = atual.proximo;
        }
        System.out.println();
    }

    public List<ListarFilaDTO> getPosicoes() {
        List<ListarFilaDTO> posicoes = new ArrayList<>();

        if (isEmpty()) {
            return posicoes;
        }

        No<T> atual = inicio;
        int posicao = 0;

        while (atual != null) {
            ListarFilaDTO listarFilaDTO = new ListarFilaDTO();
            listarFilaDTO.setNome(atual.usuario.getNome());
            listarFilaDTO.setPosicao(posicao);
            posicoes.add(listarFilaDTO);
            atual = atual.proximo;
            posicao++;
        }

        return posicoes;
    }

}
