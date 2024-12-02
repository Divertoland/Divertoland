package com.websocket.divertoland.api.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websocket.divertoland.api.config.security.helpers.JWTHelper;
import com.websocket.divertoland.domain.dtos.LoginDTO;
import com.websocket.divertoland.domain.models.Usuario;
import com.websocket.divertoland.services.security.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService _authService;

    @Autowired
    private JWTHelper _jwtHelper;
    
    @PostMapping("/register")
    public ResponseEntity<?> cadastro(@RequestBody Usuario usuario){ 
        _authService.createVisitorAccount(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){ 

        _authService.login(loginDTO);

        Cookie jwtCookie = new Cookie("JWT", _jwtHelper.generateToken(SecurityContextHolder.getContext().getAuthentication()));
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge((int) JWTHelper.expiration);
        response.addCookie(jwtCookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
