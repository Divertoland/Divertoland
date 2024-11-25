package com.websocket.divertoland.controllers;

import com.websocket.divertoland.domain.User;
import com.websocket.divertoland.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<User> login(String email, String password){
        var user = userService.login(email,password);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody User user){
        userService.createVisitorAccount(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
