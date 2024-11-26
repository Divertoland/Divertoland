package com.websocket.divertoland.domain;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Brinquedo {
    private String nome;
    private String descricao;
    private Long capacidade;
    private Long tempoEspera;
}
