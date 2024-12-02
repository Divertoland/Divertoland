package com.websocket.divertoland.services.v1;

import com.websocket.divertoland.api.config.components.FilasAtracoes;
import com.websocket.divertoland.api.config.security.CustomPasswordEncoder;
import com.websocket.divertoland.infrastructure.abstractions.repositories.AtracaoRepository;
import com.websocket.divertoland.infrastructure.abstractions.repositories.UsuarioRepository;
import com.websocket.divertoland.services.abstractions.UsuarioService;
import com.websocket.divertoland.domain.dtos.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dtos.LoginDTO;
import com.websocket.divertoland.domain.dtos.UsuarioDTO;
import com.websocket.divertoland.domain.models.Atracao;
import com.websocket.divertoland.domain.models.Usuario;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceV1 implements UsuarioService  {

    @Autowired
    private UsuarioRepository _usuarioRepository;

    @Autowired
    private FilasAtracoes _filasAtracoes;

    @Autowired
    private AtracaoRepository _atracaoRepository;

    public Usuario getUsuarioByEmail(String email) {
        return _usuarioRepository.findByEmail(email).orElseThrow();
    }
    
    public void entrarNaFila(EntrarFilaRequestDTO entrarFilaRequestDTO){
        _filasAtracoes.AdicionarUsuarioHashMp(entrarFilaRequestDTO.getAtracaoId(),entrarFilaRequestDTO.getUsuario());
        Usuario usuarioBD = _usuarioRepository.findById(entrarFilaRequestDTO.getUsuario().getId()).orElseThrow();
        Atracao atracao = _atracaoRepository.findById(entrarFilaRequestDTO.getAtracaoId()).orElseThrow();
        var posicao = _filasAtracoes.ObterPosicaoFila(entrarFilaRequestDTO.getAtracaoId(),entrarFilaRequestDTO.getUsuario());
        usuarioBD.setAtracao(atracao);
        usuarioBD.setPosicaoFila(posicao+1);
        _usuarioRepository.save(usuarioBD);
    }
    
    public void sairDaFila(Long atracaoId){
        var usuario = _filasAtracoes.getInicio(atracaoId);
        _filasAtracoes.RemoverUsuarioFila(atracaoId);
        Usuario usuarioBD = _usuarioRepository.findById(usuario.value.getId()).orElseThrow();
        usuarioBD.setAtracao(null);
        _usuarioRepository.save(usuarioBD);
    }

    public UsuarioDTO findById(Long usuarioId){
        var usuario = _usuarioRepository.findById(usuarioId).orElseThrow();
        return new UsuarioDTO(usuario);
    }
}
