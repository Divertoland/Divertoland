package com.websocket.divertoland.services.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.websocket.divertoland.domain.Usuario;
import com.websocket.divertoland.infrastructure.abstractions.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository _userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var usuarioEncontrado = _userRepository.findByEmail(email);
        if(usuarioEncontrado.isPresent()) {
            var usuario = usuarioEncontrado.get();
            
            return new User(
                usuario.getEmail(), 
                usuario.getSenha(), 
                Collections.singletonList(
                    new SimpleGrantedAuthority(usuario.getCargo().toString())
                )
            );
    }
    else
        throw new UsernameNotFoundException("Usuário com email "+email+" não encontrado");
    }
}
