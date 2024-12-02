package com.websocket.divertoland.services.v1;

import com.websocket.divertoland.api.config.components.FilasAtracoes;
import com.websocket.divertoland.infrastructure.abstractions.repositories.AtracaoRepository;
import com.websocket.divertoland.infrastructure.abstractions.repositories.UsuarioRepository;
import com.websocket.divertoland.services.abstractions.UsuarioService;
import com.websocket.divertoland.domain.dtos.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dtos.UsuarioDTO;
import com.websocket.divertoland.domain.models.Atracao;
import com.websocket.divertoland.domain.models.Usuario;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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
        var posicao = _filasAtracoes.Tamanho(entrarFilaRequestDTO.getAtracaoId());
        usuarioBD.setAtracao(atracao);
        usuarioBD.setPosicaoFila(posicao);
        _usuarioRepository.save(usuarioBD);
    }
    
    public void sairDaFila(long usuarioId, Long atracaoId){

        Usuario usuarioBD = _usuarioRepository
            .findById(usuarioId)
            .orElseThrow(() -> new NoSuchElementException("Não foi encontrado o usuário de ID " + usuarioId));
        
        _filasAtracoes.listarFila(atracaoId);
        
        _filasAtracoes.removerUsuarioFila(usuarioId, atracaoId);
        _filasAtracoes.listarFila(atracaoId);

        var posicaoFilaUsuarioRemovido = usuarioBD.getPosicaoFila();

        usuarioBD.setAtracao(null);
        usuarioBD.setPosicaoFila(0);
        _usuarioRepository.save(usuarioBD);

        _usuarioRepository.updatePosicoesFila(posicaoFilaUsuarioRemovido, atracaoId);
    }

    public UsuarioDTO findById(Long usuarioId){
        var usuario = _usuarioRepository.findById(usuarioId).orElseThrow();
        return new UsuarioDTO(usuario);
    }
}
