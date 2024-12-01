package com.websocket.divertoland.services.abstractions;

import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.domain.dto.UsuarioDTO;

public interface UsuarioService
{
    Usuario getUsuarioByLogin(LoginDTO loginDTO);
    void entrarNaFila(EntrarFilaRequestDTO entrarFilaRequestDTO);
    void sairDaFila(Long atracaoId);
    int posicaoDaFila(EntrarFilaRequestDTO entrarFilaRequestDTO);
    UsuarioDTO findById(Long usuarioId);
}
