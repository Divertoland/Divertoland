package com.websocket.divertoland.api.config.security.helpers;

import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTHelper {

	public static final long expiration = 5 * 60 * 1000;
	private static final SecretKey key = Jwts.SIG.HS512.key().build();
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + expiration);
		
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.claim("roles", authorities)
				.expiration(expireDate)
				.signWith(key, Jwts.SIG.HS512)
				.compact();
	}

	public String getUsernameFromJWT(String token){
		return Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT expirou ou está incorreto", ex.fillInStackTrace());
		}
	}

}
