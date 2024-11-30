package com.websocket.divertoland.services.abstractions;

import java.util.concurrent.CompletableFuture;

import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.domain.dto.UsuarioDTO;

public interface UserService 
{
    CompletableFuture<Void> createVisitorAccountAsync(Usuario usuario);
    CompletableFuture<Usuario> loginAsync(LoginDTO loginDTO);
    CompletableFuture<Void> entrarNaFila(EntrarFilaRequestDTO entrarFilaRequestDTO);
    CompletableFuture<Void> sairDaFila(Long atracaoId);
    CompletableFuture<Integer> posicaoDaFila(EntrarFilaRequestDTO entrarFilaRequestDTO);
}
