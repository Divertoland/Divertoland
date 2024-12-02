package com.websocket.divertoland.services.abstractions;

import java.util.List;

import com.websocket.divertoland.domain.models.Atracao;

public interface AtracaoService 
{
    List<Atracao> listAtracoesAsync();
}
