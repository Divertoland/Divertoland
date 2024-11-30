package com.websocket.divertoland.infrastructure.abstractions.repositories;

import com.azure.core.models.GeoObjectType;
import com.websocket.divertoland.domain.Atracao;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AtracaoRepository extends JpaRepository<Atracao, Long> {
        
}
