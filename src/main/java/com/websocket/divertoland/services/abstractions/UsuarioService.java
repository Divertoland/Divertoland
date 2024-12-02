package com.websocket.divertoland.services.abstractions;

import com.websocket.divertoland.domain.dtos.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dtos.UsuarioDTO;
import com.websocket.divertoland.domain.models.Usuario;

public interface UsuarioService
{
    Usuario getUsuarioByEmail(String email);
    void entrarNaFila(EntrarFilaRequestDTO entrarFilaRequestDTO);
    void sairDaFila(long usuarioId, Long atracaoId);
    UsuarioDTO findById(Long usuarioId);
}
