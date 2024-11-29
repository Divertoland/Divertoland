package com.websocket.divertoland.api.controllers.v1;

import com.websocket.divertoland.domain.Usuario;
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
@RequestMapping("/v1/data/user")
public class UserController {

    @Autowired
    private UserService _userService;

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


}
