package com.websocket.divertoland.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

import com.websocket.divertoland.api.config.security.CustomPasswordEncoder;
import com.websocket.divertoland.api.config.security.filters.DebugFilter;
import com.websocket.divertoland.api.config.security.filters.JWTAuthenticationFilter;
import com.websocket.divertoland.api.config.security.handlers.SpaCsrfTokenRequestHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static String[] notAuthenticatedRoutes = new String[]{
        "/assets/**",
        "/pages/**",
        "/cadastro",
        "/login",
        "/api/auth/**"
    };

    @Value("${spring.profiles.active}")
    private String _environment;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity = httpSecurity
            .csrf(config -> { config
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

                // Handler preciso para proteção contra BREACH na verificação do CSRF
                // Implementação buscada da própria docs do Spring
                // https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#csrf-integration-javascript
                .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler());
            })
            .sessionManagement(config -> { config
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .authorizeHttpRequests(config -> { config
                .requestMatchers(notAuthenticatedRoutes).permitAll()
                .anyRequest().authenticated();
            })
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // Filter de debug para ajudar no desenvolvimento
        if(_environment.equals("local"))
            httpSecurity = httpSecurity
                .addFilterBefore(new DebugFilter(), CsrfFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new CustomPasswordEncoder();
    }

    // FILTERS
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Profile("local")
    @Bean
    public DebugFilter debugFilter() {
        return new DebugFilter();
    }

    // HANDLERS
    @Bean
    public SpaCsrfTokenRequestHandler spaCsrfTokenRequestHandler() {
        return new SpaCsrfTokenRequestHandler();
    }
}
