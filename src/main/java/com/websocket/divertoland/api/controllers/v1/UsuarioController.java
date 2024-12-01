package com.websocket.divertoland.api.controllers.v1;

import com.websocket.divertoland.api.config.ComponentConfig;
import com.websocket.divertoland.domain.dto.EntrarFilaRequestDTO;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.services.abstractions.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService _usuarioService;
    @Autowired
    private ComponentConfig _componentConfig;

    @PostMapping("/get")
    public ResponseEntity<?> getUsuarioByLogin(@RequestBody LoginDTO loginDTO){
        var user = _usuarioService.getUsuarioByLogin(loginDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/entrar-fila-brinquedo")
    public ResponseEntity<?> entrarFila(@RequestBody EntrarFilaRequestDTO entrarFilaRequestDTO){ 
        _usuarioService.entrarNaFila(entrarFilaRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{atracaoId}/sair-fila-brinquedo")
    public ResponseEntity<?> sairFila(@PathVariable Long atracaoId){
        _usuarioService.sairDaFila(atracaoId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/posicao-fila")
    public ResponseEntity<?>obterPosicaoFila(@RequestBody EntrarFilaRequestDTO entrarFilaRequestDTO){
        var posicao = _usuarioService.posicaoDaFila(entrarFilaRequestDTO);
        return ResponseEntity.ok(posicao);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> findById(@PathVariable Long userId){
        var user = _usuarioService.findById(userId);
        return ResponseEntity.ok(user);
    }
}
