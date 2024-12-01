package com.websocket.divertoland.api.controllers.v1;

import com.websocket.divertoland.api.config.ComponentConfig;
import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.services.abstractions.UsuarioService;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService _usuarioService;
    @Autowired
    private ComponentConfig _componentConfig;

    @Async
    @PostMapping("/entrar-fila-brinquedo")
    public CompletableFuture<ResponseEntity<?>> entrarFila(@RequestBody EntrarFilaRequestDTO entrarFilaRequestDTO){ return CompletableFuture.supplyAsync(() ->
    {
        _usuarioService.entrarNaFila(entrarFilaRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    });}

    @Async
    @PostMapping("/{atracaoId}/sair-fila-brinquedo")
    public CompletableFuture<ResponseEntity<?>> sairFila(@PathVariable Long atracaoId){ return CompletableFuture.supplyAsync(() ->
    {
        _usuarioService.sairDaFila(atracaoId);
        return ResponseEntity.status(HttpStatus.OK).build();
    });}

    @Async
    @PostMapping("/posicao-fila")
    public CompletableFuture<ResponseEntity<?>> obterPosicaoFila(@RequestBody EntrarFilaRequestDTO entrarFilaRequestDTO){
        return CompletableFuture.supplyAsync(() ->{
            var posicao = _usuarioService.posicaoDaFila(entrarFilaRequestDTO).join();
            return ResponseEntity.ok(posicao);
        });
    }
}
