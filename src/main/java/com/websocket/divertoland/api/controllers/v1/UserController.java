package com.websocket.divertoland.api.controllers.v1;

import com.websocket.divertoland.api.config.ComponentConfig;
import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.services.abstractions.UserService;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService _userService;
    @Autowired
    private ComponentConfig _componentConfig;

    @Async
    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<?>> login(@RequestBody LoginDTO loginDTO){ return CompletableFuture.supplyAsync(() ->
    {
        var user = _userService.loginAsync(loginDTO).join();
        return ResponseEntity.ok(user);
    });}

    @Async
    @PostMapping("/cadastro")
    public CompletableFuture<ResponseEntity<?>> cadastro(@RequestBody Usuario usuario){ return CompletableFuture.supplyAsync(() ->
    {
        _userService.createVisitorAccountAsync(usuario).join();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    });}

    @Async
    @PostMapping("/entrar-fila-brinquedo")
    public CompletableFuture<ResponseEntity<?>> entrarFila(@RequestBody EntrarFilaRequestDTO entrarFilaRequestDTO){ return CompletableFuture.supplyAsync(() ->
    {
        _userService.entrarNaFila(entrarFilaRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    });}

    @Async
    @PostMapping("/{atracaoId}/sair-fila-brinquedo")
    public CompletableFuture<ResponseEntity<?>> sairFila(@PathVariable Long atracaoId){ return CompletableFuture.supplyAsync(() ->
    {
        _userService.sairDaFila(atracaoId);
        return ResponseEntity.status(HttpStatus.OK).build();
    });}

    @Async
    @PostMapping("/posicao-fila")
    public CompletableFuture<ResponseEntity<?>> obterPosicaoFila(@RequestBody EntrarFilaRequestDTO entrarFilaRequestDTO){
        return CompletableFuture.supplyAsync(() ->{
            var posicao = _userService.posicaoDaFila(entrarFilaRequestDTO).join();
            return ResponseEntity.ok(posicao);
        });
    }

    @Async
    @GetMapping("{userId}")
    public CompletableFuture<ResponseEntity<?>> findById(@PathVariable Long userId){
        return CompletableFuture.supplyAsync(() -> {
        var user = _userService.findById(userId).join();
        return ResponseEntity.ok(user);
        });
    }
}
