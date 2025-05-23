package com.ofektom.med.utils;


import com.ofektom.med.serviceImpl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private JwtUtils utils;
    private UserServiceImpl userService;


    @Autowired
    public JwtAuthenticationFilter(JwtUtils utils, @Lazy UserServiceImpl userService) {
        this.utils = utils;
        this.userService = userService;
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String authenticationHeader = request.getHeader("Authorization");
        System.out.println("Processing request: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("Authorization Header: " + authenticationHeader);

        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            token = authenticationHeader.substring(7).trim(); // Trim to handle extra spaces
            System.out.println("Extracted Token: " + token.substring(0, 10) + "..."); // Log first 10 chars for brevity
            String username = utils.extractUsername.apply(token);
            System.out.println("Extracted Username: " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);
                System.out.println("User Details Loaded: " + (userDetails != null));
                if (userDetails != null && utils.isTokenValid.apply(token, userDetails.getUsername())) {
                    System.out.println("Token is valid for user: " + username);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    System.out.println("Authentication set for user: " + username);
                } else {
                    System.out.println("Token validation failed or user not found");
                }
            } else {
                System.out.println("Skipping authentication: Username null or context already set");
            }
        } else {
            System.out.println("No valid Authorization header found");
        }

        filterChain.doFilter(request, response);
    }
}