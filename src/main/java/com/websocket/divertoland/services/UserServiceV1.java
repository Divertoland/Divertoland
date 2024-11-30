package com.websocket.divertoland.services;

import com.websocket.divertoland.api.config.ComponentConfig;
import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.domain.dto.UsuarioDTO;
import com.websocket.divertoland.infrastructure.abstractions.repositories.AtracaoRepository;
import com.websocket.divertoland.infrastructure.abstractions.repositories.UserRepository;
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

    @Autowired
    private ComponentConfig _componentConfig;

    @Autowired
    private AtracaoRepository atracaoRepository;

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
    @Async
    public CompletableFuture<Void> entrarNaFila(EntrarFilaRequestDTO entrarFilaRequestDTO){
        return CompletableFuture.runAsync(() ->
        {
            _componentConfig.AdicionarUsuarioHashMp(entrarFilaRequestDTO.getAtracaoId(),entrarFilaRequestDTO.getUsuario());
            Usuario usuarioBD = userRepository.findById(entrarFilaRequestDTO.getUsuario().getId()).orElseThrow();
            Atracao atracao = atracaoRepository.findById(entrarFilaRequestDTO.getAtracaoId()).orElseThrow();
            usuarioBD.setAtracao(atracao);
            userRepository.save(usuarioBD);
        });
    }

    @Async
    public CompletableFuture<Void> sairDaFila(Long atracaoId){
        return CompletableFuture.runAsync(() ->
        {
        var user = _componentConfig.getIncio(atracaoId);
        _componentConfig.RemoverUsuarioFila(atracaoId);
        Usuario usuarioBD = userRepository.findById(user.usuario.getId()).orElseThrow();
        usuarioBD.setAtracao(null);
        userRepository.save(usuarioBD);
    });
    }

    @Async
    public CompletableFuture<Integer> posicaoDaFila(EntrarFilaRequestDTO entrarFilaRequestDTO){
        return CompletableFuture.supplyAsync(() ->
                _componentConfig.ObterPosicaoFila(entrarFilaRequestDTO.getAtracaoId(),entrarFilaRequestDTO.getUsuario()));
    }



}
