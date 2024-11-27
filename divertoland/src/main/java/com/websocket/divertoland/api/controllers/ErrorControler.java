package com.websocket.divertoland.api.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class ErrorControler implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Retorne o nome da página HTML ou o template de erro
        return "error"; // Esse é o nome do template que você pode criar
    }

}
