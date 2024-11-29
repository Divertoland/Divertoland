package com.websocket.divertoland.infrastructure.abstractions.repositories;

import com.websocket.divertoland.domain.Atracao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AtracaoRepository extends JpaRepository<Atracao, Long> {
    
    @Query(value = "SELECT tempo_espera FROM atracao WHERE id = :idAtracao")
    int getAttractionWaitingTime(@Param("idAtracao") int idAtracao);

    @Query(value = "SELECT a.id AS atracao_id, a.nome AS atracao_nome, a.capacidade, a.duracao, (COUNT(u.id) / a.capacidade) * a.duracao AS tempo_espera_calculado FROM atracao a LEFT JOIN usuario u ON a.id = u.atracao_id WHERE a.id = :idAtracao && u.atracao_id = :idAtracao GROUP BY a.id, a.nome, a.capacidade, a.duracao;")
    int calculateAttractionWaitingTime(@Param("idAtracao") int idAtracao);

    @Query(value = "UPDATE atracao SET tempo_espera = :tempoEsperaCalculado WHERE id = :idAtracao")
    void updateAttractionWaitingTime(@Param("tempoEsperaCalculado") float tempoEsperaCalculado, @Param("idAtracao") int idAtracao);
}
