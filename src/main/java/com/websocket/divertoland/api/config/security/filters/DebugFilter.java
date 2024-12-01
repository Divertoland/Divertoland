package com.websocket.divertoland.api.config.security.filters;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DebugFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String xsrfToken = request.getHeader("X-XSRF-TOKEN");
        System.out.println("Received XSRF Token: " + xsrfToken);
        filterChain.doFilter(request, response);
    }
}