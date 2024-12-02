package com.websocket.divertoland.infrastructure.abstractions.repositories;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.websocket.divertoland.domain.models.Atracao;

@Repository
public interface AtracaoRepository extends JpaRepository<Atracao, Long> {

    @Transactional
    @Query(value = "SELECT tempo_espera FROM atracao WHERE id = :idAtracao", nativeQuery = true)
    int getAttractionWaitingTime(@Param("idAtracao") int idAtracao);

    @Modifying
    @Transactional
    @Query(value = "WITH CalculatedWaitTime AS (" + //
            "SELECT " + //
            "a.id AS atracao_id," + //
            "(COUNT(u.id) / a.capacidade) * a.duracao AS tempo_espera_calculado" + //
            "FROM " + //
            "atracao a" + //
            "LEFT JOIN " + //
            "usuario u " + //
            "ON " + //
            "a.id = u.atracao_id" + //
            "WHERE " + //
            "a.id = :idAtracao AND u.atracao_id = :idAtracao" + //
            "GROUP BY " + //
            "a.id, a.capacidade, a.duracao" + //
            ")" + //
            "UPDATE atracao" + //
            "SET tempo_espera = (SELECT tempo_espera_calculado FROM CalculatedWaitTime)" + //
            "WHERE id = :idAtracao;", nativeQuery = true)
    void updateAttractionWaitingTime(@Param("idAtracao") int idAtracao);


}
