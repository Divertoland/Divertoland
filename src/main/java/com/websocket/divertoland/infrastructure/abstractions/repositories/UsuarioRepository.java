package com.websocket.divertoland.infrastructure.abstractions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.websocket.divertoland.domain.models.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByEmailAndSenha(String email, String password);
    Optional<Usuario> findByEmail(String email);
}
