package com.websocket.divertoland.services.abstractions;

import java.util.concurrent.CompletableFuture;

import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;

public interface UsuarioService
{
    CompletableFuture<Void> entrarNaFila(EntrarFilaRequestDTO entrarFilaRequestDTO);
    CompletableFuture<Void> sairDaFila(Long atracaoId);
    CompletableFuture<Integer> posicaoDaFila(EntrarFilaRequestDTO entrarFilaRequestDTO);
}
