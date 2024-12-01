package com.websocket.divertoland.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.websocket.divertoland.domain.Role;
import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.domain.dto.LoginDTO;
import com.websocket.divertoland.infrastructure.abstractions.repositories.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository _usuarioRepository;

    @Autowired
    private AuthenticationManager _authenticationManager;

    @Autowired
    private PasswordEncoder _passwordEncoder;

    public void createVisitorAccount(Usuario usuario) {
        usuario.setSenha(_passwordEncoder.encode(usuario.getSenha()));
        usuario.setCargo(Role.VISITANTE);
        _usuarioRepository.save(usuario);
    }

    public void login(LoginDTO loginDTO) {
        var authentication = _authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
