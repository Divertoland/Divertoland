package com.websocket.divertoland.api.config;

import java.util.ArrayList;
import java.util.List;
import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.structures.HashMap;

class FilasAtracoes {
    
    private HashMap<Atracao, List<Usuario>> filasAtracoes;

    public void AdicionarUsuarioFila(Usuario usuario, Atracao atracao){
        if (filasAtracoes.get(atracao).isEmpty()){
            filasAtracoes.get(atracao).add(usuario);
        } else {
            filasAtracoes.put(atracao, new ArrayList<Usuario>());
            filasAtracoes.get(atracao).add(usuario);
        }
    }

    public void RemoverUsuarioFIla(Usuario usuario, Atracao atracao){
        if (!filasAtracoes.get(atracao).isEmpty()){
            filasAtracoes.get(atracao).remove(0);
        }
    }
}
