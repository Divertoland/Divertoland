package com.websocket.divertoland.domain.structures;

public class Fila<T> {
    private No<T> inicio, fim;
    private int tamanho;

    public No<T> getInicio() { return inicio; }

    public void setInicio(No<T> inicio) { this.inicio = inicio; }

    public No<T> getFim() { return fim; }

    public void setFim(No<T> fim) { this.fim = fim; }

    public void setTamanho(int tamanho) { this.tamanho = tamanho; }

    public Fila() {
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    public boolean isEmpty() { return (inicio == null); }

    public void enqueue(T dado)
    {
        No<T> novoNo = new No<T>(dado);
        if (isEmpty()) {
            inicio = novoNo;
        } else {
            fim.proximo = novoNo;
        }
        fim = novoNo;
        tamanho++;
    }

    public T dequeue() {
        if (isEmpty())
            return null;

        No<T> temp = inicio;
        inicio = inicio.proximo;

        if(inicio==null) 
            fim = null;

        tamanho--;
        return temp.value;
    }

    public int size() { return tamanho; }

    public int index(T valueToFind) {
        No<T> atual = fim; // Começa pelo fim da fila
        int posicao = 0;      // Contador para a posição

        while (atual != null) {
            if (atual.value.equals(valueToFind))
                return posicao; // Retorna a posição do usuário se encontrado

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
            System.out.print(atual.value + " \n");
            atual = atual.proximo;
        }
    }

}
