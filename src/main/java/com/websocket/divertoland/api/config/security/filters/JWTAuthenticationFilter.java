package com.websocket.divertoland.api.config.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.websocket.divertoland.api.config.SecurityConfig;
import com.websocket.divertoland.api.config.security.helpers.JWTHelper;
import com.websocket.divertoland.services.security.UsuarioDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTHelper _jwtHelper;

    @Autowired
    private UsuarioDetailsService _usuarioDetailsService;

    private List<RequestMatcher> _notAuthenticatedRoutesMatchers;

    public JWTAuthenticationFilter(){
        super();
        _notAuthenticatedRoutesMatchers = new ArrayList<RequestMatcher>();
        
        for (String route : SecurityConfig.notAuthenticatedRoutes) {
            _notAuthenticatedRoutesMatchers.add(new AntPathRequestMatcher(route));
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(!_notAuthenticatedRoutesMatchers.stream().anyMatch(matcher -> matcher.matches(request))){    
            String token = getJWTFromRequest(request);

            if(StringUtils.hasText(token)){
                try{
                    if(_jwtHelper.validateToken(token)){
                        String username = _jwtHelper.getUsernameFromJWT(token);
    
                        UserDetails userDetails = _usuarioDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(), 
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                    else{
                        response.sendRedirect("/login");
                        return;
                    }
                }
                catch(Exception ex){
                    System.err.println(ex.getMessage());
                    response.sendRedirect("/login");
                    return;
                }
            }
            else{
                response.sendRedirect("/login");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {

        // Tenta buscar pelo bearer
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        // Tenta buscar por cookie
        else{
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("JWT".equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }
}
