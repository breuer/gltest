package com.gl.usermicroservice.service;

import static java.time.ZoneOffset.UTC;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    @Value("${jwt.signing.key}")
    private String signingKey;
	
	public String generateToken(String subject) {
		
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
		
    	Date expiration = Date.from(LocalDateTime.now(UTC).plusMinutes(15).toInstant(UTC));
    	
        String jwt = Jwts.builder()
                .setClaims(Map.of("username", subject))
                .signWith(key)
    			.setExpiration(expiration)
                .compact();
        
        return jwt;
	}
	
	public String getUsernameFromToken(String token) {
		
	    	SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
	    	
	        Claims claims = Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	        
	        String username = String.valueOf(claims.get("username"));
	        
		return username;
	}

}
