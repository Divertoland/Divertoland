package com.websocket.divertoland.domain;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String senha;
    private Role cargo;

    public Usuario(){}
    
    public Usuario(String name, String email, String senha) {
        this.name = name;
        this.email = email;
        this.senha = senha;
    }
}
