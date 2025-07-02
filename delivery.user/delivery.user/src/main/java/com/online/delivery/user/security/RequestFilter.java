package com.online.delivery.user.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.online.delivery.user.services.CustomeUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestFilter extends OncePerRequestFilter {
	Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	CustomeUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("JWT");
        String token = null;
        String username = null;
        logger.info("header: " + header);
		if(header != null && !header.isEmpty() && header.startsWith("Bearer")){
			token = header.substring(7);
            username = jwtUtil.loadUserNameFromToken(token);
		}
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = customUserDetailsService.loadUserByUsername(username);
            logger.info("user: " + user);
            if (jwtUtil.validateToken(token, user)) {
                UsernamePasswordAuthenticationToken upat = 
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            } else {
                System.out.println("Token validation failed");
            }
        }
        filterChain.doFilter(request, response);
	}
    
}