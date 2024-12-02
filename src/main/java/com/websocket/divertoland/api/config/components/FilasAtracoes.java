package com.websocket.divertoland.api.config.components;

import com.websocket.divertoland.domain.dtos.UsuarioDTO;
import com.websocket.divertoland.domain.structures.Fila;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.websocket.divertoland.domain.structures.HashMap;
import com.websocket.divertoland.domain.structures.No;

@Component
public class FilasAtracoes {

    private final HashMap<Long, Fila<UsuarioDTO>> filasAtracoes = new HashMap<>();

    public void AdicionarUsuarioHashMp(Long atracaoId, UsuarioDTO usuario){
        if(filasAtracoes.get(atracaoId) != null)
            filasAtracoes.get(atracaoId).enqueue(usuario);
        else {
            var novaFila = new Fila<UsuarioDTO>();
            novaFila.enqueue(usuario);
            filasAtracoes.put(atracaoId, novaFila);
        }

    }

    public void removerUsuarioFila(long usuarioId, Long atracaoId){
        if (this.getFila(atracaoId).isEmpty()) {
            throw new NoSuchElementException("A fila está vazia!");
        }
    
        No<UsuarioDTO> atual = this.getFila(atracaoId).getInicio();
        No<UsuarioDTO> anterior = null;
    
        while (atual != null) {
            if (atual.value.getId().equals(usuarioId)) {
                if (atual == this.getFila(atracaoId).getInicio()) {
                    this.getFila(atracaoId).setInicio(atual.proximo);
                    if (this.getFila(atracaoId).getInicio() == null) {
                        this.getFila(atracaoId).setFim(null);
                    }
                } else {
                    anterior.proximo = atual.proximo;
                    if (atual == this.getFila(atracaoId).getFim()) {
                        this.getFila(atracaoId).setFim(anterior);
                    }
                }
                return;
            }
    
            anterior = atual;
            atual = atual.proximo;
        }
    
        throw new NoSuchElementException("Usuário não encontrado na fila!");
    }
    public No<UsuarioDTO> getInicio(Long atracaoId){
        return filasAtracoes.get(atracaoId).getInicio();
    }

    public Fila<UsuarioDTO> getFila(long atracaoId){
        return filasAtracoes.get(atracaoId);
    }

    public void listarFila (Long atracaoId){
        filasAtracoes.get(atracaoId).display();
    }

    public boolean atracaoPossuiFila(long atracaoId){
        return filasAtracoes.possuiChave(atracaoId);
    }

    public int ObterPosicaoFila(Long atracaoId, UsuarioDTO usuario){
        return filasAtracoes.get(atracaoId).index(usuario);
    }
}
