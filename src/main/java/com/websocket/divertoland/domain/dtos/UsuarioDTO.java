package com.websocket.divertoland.domain.dtos;

import com.websocket.divertoland.domain.models.Usuario;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nome;
    private int posicaoFila;
    private AtracaoDTO atracao;    

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.posicaoFila = usuario.getPosicaoFila();
        if (usuario.getAtracao() != null){
            this.atracao = new AtracaoDTO();
            this.atracao.setId(usuario.getAtracao().getId());
            this.atracao.setNome(usuario.getAtracao().getNome());
        };
    }
    public UsuarioDTO(){

    }
}
