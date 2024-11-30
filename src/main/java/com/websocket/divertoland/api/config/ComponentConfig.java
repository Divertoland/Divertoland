package com.websocket.divertoland.api.config;

import com.websocket.divertoland.domain.No;
import com.websocket.divertoland.domain.dto.UsuarioDTO;
import com.websocket.divertoland.domain.structures.Fila;
import org.springframework.stereotype.Component;

import com.websocket.divertoland.domain.structures.HashMap;

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

    public int ObterPosicaoFila(Long atracaoId, UsuarioDTO usuario){
        return filasAtracoes.get(atracaoId).posicaoUsuario(usuario);
    }

    public No<UsuarioDTO> getInicio(Long atracaoId){
        return filasAtracoes.get(atracaoId).getInicio();
    }




}
