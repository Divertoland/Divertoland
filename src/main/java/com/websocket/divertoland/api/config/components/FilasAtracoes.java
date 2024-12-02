package com.websocket.divertoland.api.config.components;

import com.websocket.divertoland.domain.dtos.UsuarioDTO;
import com.websocket.divertoland.domain.structures.Fila;
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

    public void RemoverUsuarioFila(Long atracaoId, long idUsuario){
        if (this.atracaoPossuiFila(atracaoId)){

            var noAtual = filasAtracoes.get(atracaoId).getFim();

            while(noAtual.value.getId() != idUsuario){
                noAtual = noAtual.proximo;
            }

            
        }
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
}
