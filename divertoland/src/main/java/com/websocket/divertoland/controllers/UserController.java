package com.websocket.divertoland.controllers;

import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/data/user")
public class UserController {

    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Usuario> login(LoginDTO loginDTO){
        var user = userService.login(loginDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody Usuario usuario){
        userService.createVisitorAccount(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
