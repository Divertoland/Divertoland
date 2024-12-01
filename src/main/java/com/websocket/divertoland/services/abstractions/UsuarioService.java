package com.websocket.divertoland.services.abstractions;

import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;

public interface UsuarioService
{
    void entrarNaFila(EntrarFilaRequestDTO entrarFilaRequestDTO);
    void sairDaFila(Long atracaoId);
    int posicaoDaFila(EntrarFilaRequestDTO entrarFilaRequestDTO);
}
