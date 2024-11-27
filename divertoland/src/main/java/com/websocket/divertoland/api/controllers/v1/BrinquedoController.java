package com.websocket.divertoland.api.controllers.v1;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/v1/data/brinquedo")
public class BrinquedoController {

    @Async
    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<?>> listarBrinquedo(){ return CompletableFuture.supplyAsync(() ->
    {
        String brinquedo = null;
        return ResponseEntity.ok(brinquedo);
    });}
}
