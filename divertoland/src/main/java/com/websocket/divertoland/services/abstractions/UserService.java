package com.websocket.divertoland.services.abstractions;

import java.util.concurrent.CompletableFuture;

import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.LoginDTO;

public interface UserService 
{
    CompletableFuture<Void> createVisitorAccountAsync(Usuario usuario);
    CompletableFuture<Usuario> loginAsync(LoginDTO loginDTO);
}
