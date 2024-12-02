package com.websocket.divertoland.domain.structures;

import lombok.Data;

@Data
public class HashMap<K, V> {
    private static final double LOAD_FACTOR = 0.75;
    private Object[][] tabela;
    private int tamanho;

    public HashMap() {
        tabela = new Object[16][];
        tamanho = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % (tabela.length == 0 ? 1 : tabela.length);  // Evita dividir por 0 se estiver vazio
    }

    private void redimensionar() {
        // Se a tabela estiver vazia, define um tamanho inicial padrão
        int novaCapacidade = (tabela.length == 0) ? 16 : tabela.length * 2;
        Object[][] oldtabela = tabela;
        tabela = new Object[novaCapacidade][];

        // Reinsere todos os elementos na nova tabela
        for (Object[] bucket : oldtabela) {
            if (bucket != null) {
                for (int i = 0; i < bucket.length; i += 2) {
                    put((K) bucket[i], (V) bucket[i + 1]); // Reinsere chave-valor
                }
            }
        }
    }

    // Adicionar um valor ao HashMap
    public void put(K key, V valor) {
        // Verifica se é necessário redimensionar a tabela
        if (tabela.length == 0 || (double) tamanho / tabela.length >= LOAD_FACTOR) {
            redimensionar();
        }

        int index = hash(key);

        // Verifica se o bucket está vazio
        if (tabela[index] == null) {
            tabela[index] = new Object[2]; // cria um novo array com 2 elementos: chave e valor
            tabela[index][0] = key;
            tabela[index][1] = valor;
            tamanho++;
        } else {
            // Se já houver colisão, percorre o array para buscar a chave
            boolean atualizado = false;
            for (int i = 0; i < tabela[index].length; i += 2) {
                if (tabela[index][i] != null && tabela[index][i].equals(key)) {
                    tabela[index][i + 1] = valor; // Atualiza o valor se a chave já existir
                    atualizado = true;
                    break;
                }
            }

            if (!atualizado) {
                // Caso não encontre a chave, expande o array para adicionar o novo par chave-valor
                Object[] novoValor = new Object[tabela[index].length + 2];
                System.arraycopy(tabela[index], 0, novoValor, 0, tabela[index].length);
                novoValor[tabela[index].length] = key;
                novoValor[tabela[index].length + 1] = valor;
                tabela[index] = novoValor;
                tamanho++;
            }
        }
    }

    public V get(K key) {
        int index = hash(key);
        if (tabela[index] != null) {
            for (int i = 0; i < tabela[index].length; i += 2) {
                if (tabela[index][i].equals(key)) {
                    return (V) tabela[index][i + 1];
                }
            }
        }
        return null; // Se a chave não for encontrada
    }

    public void remove(K key) {
        int index = hash(key);
        if (tabela[index] != null) {
            for (int i = 0; i < tabela[index].length; i += 2) {
                if (tabela[index][i].equals(key)) {
                    // Remove o par chave-valor deslocando os elementos
                    for (int j = i; j < tabela[index].length - 2; j += 2) {
                        tabela[index][j] = tabela[index][j + 2];
                        tabela[index][j + 1] = tabela[index][j + 3];
                    }
                    tabela[index][tabela[index].length - 2] = null;
                    tabela[index][tabela[index].length - 1] = null;
                    tamanho--;
                    break;
                }
            }
        }
    }

    // Verificar se a chave existe no mapa
    public boolean possuiChave(K key) {
        int index = hash(key);
        if (tabela[index] != null) {
            for (int i = 0; i < tabela[index].length; i += 2) {
                if (tabela[index][i].equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Retorna o número de elementos no mapa
    public int tamanho() {
        return tamanho;
    }
}
