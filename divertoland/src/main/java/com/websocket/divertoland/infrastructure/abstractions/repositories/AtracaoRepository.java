package com.websocket.divertoland.infrastructure.abstractions.repositories;

import com.websocket.divertoland.domain.Atracao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AtracaoRepository extends JpaRepository<Atracao, Long> {}
