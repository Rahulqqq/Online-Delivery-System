//package com.online.delivery.user.jwtEntryPoint;
//
//import java.io.PrintWriter;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public  class JWTAthenticationEntryPoint implements AuthenticationEntryPoint {
//
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException authException) throws java.io.IOException, ServletException {
//		PrintWriter writer = response.getWriter();
//        writer.println("Access Denied !! " + authException.getMessage());
//		
//	}
//}
