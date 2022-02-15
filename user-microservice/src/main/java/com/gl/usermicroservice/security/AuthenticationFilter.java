package com.gl.usermicroservice.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gl.usermicroservice.constants.Constants;
import com.gl.usermicroservice.exception.ExceptionResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/login");
	}
	
	@Value("${jwt.signing.key}")
	private String signingKey;
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	String jwt = request.getHeader("Authorization");
		if (jwt == null || jwt.isBlank()) {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ExceptionResponse error = new ExceptionResponse(new Date(), 
					HttpServletResponse.SC_BAD_REQUEST, Constants.EMPTY_TOKEN);
			response.getWriter().write(objectMapperBuilder().build().writeValueAsString(error));
			return;
		}
      
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        
        Claims claims = null;
		try {
			claims = Jwts.parserBuilder()
			        .setSigningKey(key)
			        .build()
			        .parseClaimsJws(jwt)
			        .getBody();
		} catch (SignatureException | ExpiredJwtException 
				| UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ExceptionResponse error = new ExceptionResponse(new Date(), 
			HttpServletResponse.SC_BAD_REQUEST, Constants.INVALID_TOKEN);
			response.getWriter().write(objectMapperBuilder().build().writeValueAsString(error));
			
			return;
		}
        if(claims != null) {
	        String usernameFromToken = String.valueOf(claims.get("username"));
	        GrantedAuthority a = new SimpleGrantedAuthority("user");
	        UsernamePasswordAuthenticationToken auth = 
					new UsernamePasswordAuthenticationToken(usernameFromToken, null, List.of(a));
	        SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
    
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.modules(new JavaTimeModule());
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return builder;
    }

}
