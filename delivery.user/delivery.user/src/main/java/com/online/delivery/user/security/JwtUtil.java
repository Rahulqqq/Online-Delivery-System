package com.online.delivery.user.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.online.delivery.user.services.CustomeUserDetailsService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
	Logger logger = LoggerFactory.getLogger(getClass());
	

    
    private String SECRET_KEY = "Fj10bZu734MbWCYXXYR0DbwzuPvBpmXIq9uA7zdRsNENTRxpjV40N8EYmlYC10SPo0ElxXGxfhO5FILW0jJtJw";

    @Autowired
    CustomeUserDetailsService myUserDetailsService;
    
	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build()
				.parseClaimsJws(token).getBody();
	}

    public String loadUserNameFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
    
    public Boolean validateToken(String token, UserDetails user) {
        Claims claims = getClaims(token);
        System.out.println(claims);
        return (user.getUsername().equalsIgnoreCase(claims.getSubject()) );
        // && claims.getExpiration().before(new Date()));
    }

    public String generateToken(UserDetails user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", user.getAuthorities());
		user.getAuthorities();
		String token = Jwts.builder()
				.setClaims(claims)              
				.setSubject(String.format("%s", user.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512)
                .compact();
		System.out.println("Token: "+ token);
		return token;
	}
}
