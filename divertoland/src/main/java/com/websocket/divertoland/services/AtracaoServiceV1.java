package com.websocket.divertoland.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.infrastructure.abstractions.repositories.AtracaoRepository;
import com.websocket.divertoland.services.abstractions.AtracaoService;

@Service
public class AtracaoServiceV1 implements AtracaoService {

    @Autowired
    private AtracaoRepository _atracaoRepository;

    @Async
    public CompletableFuture<List<Atracao>> listAtracoesAsync() { return CompletableFuture.supplyAsync(() ->
    {
        return _atracaoRepository.findAll();
    });}
}
