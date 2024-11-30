package com.websocket.divertoland.services.abstractions;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.websocket.divertoland.domain.Atracao;

public interface AtracaoService 
{
    CompletableFuture<List<Atracao>> listAtracoesAsync();
}
