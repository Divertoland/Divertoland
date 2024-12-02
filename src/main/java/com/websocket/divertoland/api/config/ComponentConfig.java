package com.websocket.divertoland.api.config;

import com.websocket.divertoland.domain.dtos.UsuarioDTO;
import com.websocket.divertoland.domain.structures.Fila;
import org.springframework.stereotype.Component;

import com.websocket.divertoland.domain.structures.HashMap;
import com.websocket.divertoland.domain.structures.No;

@Component
public class ComponentConfig {

    private final HashMap<Long, Fila<UsuarioDTO>> filasAtracoes = new HashMap<>();
    private final Fila<UsuarioDTO> fila = new Fila<>();

    public void AdicionarUsuarioHashMp(Long atracaoId, UsuarioDTO usuario){
        if(filasAtracoes.get(atracaoId) != null){
            filasAtracoes.get(atracaoId).enqueue(usuario);
        }else {
            fila.enqueue(usuario);
            filasAtracoes.put(atracaoId,fila);
        }

    }

    public void RemoverUsuarioFila(Long atracaoId){
        if (!filasAtracoes.get(atracaoId).isEmpty()){
            filasAtracoes.get(atracaoId).dequeue();
        }
    }

    public No<UsuarioDTO> getInicio(Long atracaoId){
        return filasAtracoes.get(atracaoId).getInicio();
    }

    public void listarFila (Long atracaoId){
        filasAtracoes.get(atracaoId).display();
    }
}
