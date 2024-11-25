package com.websocket.divertoland.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String senha;
    private String cargo;

    public User(){

    }

    public User(String name, String email, String senha) {
        this.name = name;
        this.email = email;
        this.senha = senha;
    }
}
