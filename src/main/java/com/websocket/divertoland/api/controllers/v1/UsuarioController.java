package com.websocket.divertoland.api.controllers.v1;

import com.websocket.divertoland.domain.dtos.EntrarFilaRequestDTO;
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

    @GetMapping("/email/{emailUsuario}")
    public ResponseEntity<?> getUsuarioByEmail(@PathVariable String emailUsuario){
        var user = _usuarioService.getUsuarioByEmail(emailUsuario);
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

    @GetMapping("/id/{userId}")
    public ResponseEntity<?> findById(@PathVariable Long userId){
        var user = _usuarioService.findById(userId);
        return ResponseEntity.ok(user);
    }
}
