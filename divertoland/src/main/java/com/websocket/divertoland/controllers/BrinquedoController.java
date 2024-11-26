package com.websocket.divertoland.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/data/brinquedo")
public class BrinquedoController {


    @GetMapping("/list")
    public ResponseEntity<?> listarBrinquedo(){
         String brinquedo = null;
        return ResponseEntity.ok(brinquedo);
    }
}
