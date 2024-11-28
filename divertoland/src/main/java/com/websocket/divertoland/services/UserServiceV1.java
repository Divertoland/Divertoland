package com.websocket.divertoland.services;

import com.websocket.divertoland.domain.Brinquedo;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.repository.UserRepository;
import com.websocket.divertoland.services.abstractions.UserService;
import com.websocket.divertoland.domain.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class UserServiceV1 implements UserService {

    @Autowired
    public UserRepository userRepository;

    @Async
    public CompletableFuture<Void> createVisitorAccountAsync(Usuario usuario) {
        return CompletableFuture.runAsync(() ->
        {
            usuario.setSenha(criptografarSenha(usuario.getSenha()));
            userRepository.save(usuario);
        });
    }

    @Async
    public CompletableFuture<Usuario> loginAsync(LoginDTO loginDTO) {
        return CompletableFuture.supplyAsync(() ->
        {
            loginDTO.setSenha(criptografarSenha(loginDTO.getSenha()));
            return userRepository.findByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha()).orElseThrow();
        });
    }

    public void entrarFila(Usuario usuario, Brinquedo brinquedo) {


    }

    public String criptografarSenha(String senha) {
        MessageDigest algoritimo = null;
        try {
            algoritimo = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte messageDigest[] = algoritimo.digest(senha.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }


}
