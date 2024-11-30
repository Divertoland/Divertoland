package com.websocket.divertoland.api.controllers.v1;

import java.util.concurrent.CompletableFuture;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.services.abstractions.AtracaoService;

@Controller
@RestController
@RequestMapping("/v1/data/atracao")
public class AtracaoController {

    @Autowired
    private AtracaoService _atracaoService;

    @Async
    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<?>> listarAtracao(){ return CompletableFuture.supplyAsync(() ->
    {
        var atracoes = _atracaoService.listAtracoesAsync().join();

        if(atracoes.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else 
            return new ResponseEntity<List<Atracao>>(atracoes, HttpStatus.OK);
    });}
}
