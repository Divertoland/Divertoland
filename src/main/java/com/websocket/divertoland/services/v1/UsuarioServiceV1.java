package com.websocket.divertoland.services.v1;

import com.websocket.divertoland.api.config.ComponentConfig;
import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.infrastructure.abstractions.repositories.AtracaoRepository;
import com.websocket.divertoland.infrastructure.abstractions.repositories.UsuarioRepository;
import com.websocket.divertoland.services.abstractions.UsuarioService;
import com.websocket.divertoland.domain.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceV1 implements UsuarioService  {

    @Autowired
    private UsuarioRepository _usuarioRepository;

    @Autowired
    private ComponentConfig _componentConfig;

    @Autowired
    private AtracaoRepository _atracaoRepository;
    
    @Async
    public CompletableFuture<Void> entrarNaFila(EntrarFilaRequestDTO entrarFilaRequestDTO){
        return CompletableFuture.runAsync(() ->
        {
            _componentConfig.AdicionarUsuarioHashMp(entrarFilaRequestDTO.getAtracaoId(),entrarFilaRequestDTO.getUsuario());
            Usuario usuarioBD = _usuarioRepository.findById(entrarFilaRequestDTO.getUsuario().getId()).orElseThrow();
            Atracao atracao = _atracaoRepository.findById(entrarFilaRequestDTO.getAtracaoId()).orElseThrow();
            usuarioBD.setAtracao(atracao);
            _usuarioRepository.save(usuarioBD);
        });
    }
    
    @Async
    public CompletableFuture<Void> sairDaFila(Long atracaoId){
        return CompletableFuture.runAsync(() ->
        {
            var usuario = _componentConfig.getInicio(atracaoId);
            _componentConfig.RemoverUsuarioFila(atracaoId);
            Usuario usuarioBD = _usuarioRepository.findById(usuario.usuario.getId()).orElseThrow();
            usuarioBD.setAtracao(null);
            _usuarioRepository.save(usuarioBD);
        });
    }
    
    @Async
    public CompletableFuture<Integer> posicaoDaFila(EntrarFilaRequestDTO entrarFilaRequestDTO){
        return CompletableFuture.supplyAsync(() ->
        _componentConfig.ObterPosicaoFila(entrarFilaRequestDTO.getAtracaoId(),entrarFilaRequestDTO.getUsuario()));
    }
}
