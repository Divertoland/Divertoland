package com.websocket.divertoland.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {
    
    @GetMapping("/")
    public String home() {
        return "pages/home/home";
    }

    @GetMapping("/login")
    public String login() {
        return "pages/login/login";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "pages/cadastro/cadastro";
    }

}
