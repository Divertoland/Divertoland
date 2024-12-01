package com.websocket.divertoland.api.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websocket.divertoland.domain.Atracao;
import com.websocket.divertoland.services.abstractions.AtracaoService;

@RestController
@RequestMapping("api/v1/atracao")
public class AtracaoController {

    @Autowired
    private AtracaoService _atracaoService;

    @GetMapping("/list")
    public ResponseEntity<?> listarAtracao(){ 
        var atracoes = _atracaoService.listAtracoesAsync();

        if(atracoes.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else 
            return new ResponseEntity<List<Atracao>>(atracoes, HttpStatus.OK);
    }
}
