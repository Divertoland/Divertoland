package com.websocket.divertoland.infrastructure.abstractions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.websocket.divertoland.domain.models.Usuario;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByEmailAndSenha(String email, String password);
    Optional<Usuario> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "" +
            "UPDATE usuario " +
            "SET posicao_fila = posicao_fila - 1 " +
            "WHERE posicao_fila > :posicaoFilaUsuarioRemovidoId " +
            "AND atracao_id = :atracaoId")
    void updatePosicoesFila(@Param("posicaoFilaUsuarioRemovidoId") long posicaoFilaUsuarioRemovidoId, 
                            @Param("atracaoId") long atracaoId);
}
