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

    /**
     * Skip JWT filter for static resources and public endpoints
     * Returns true to SKIP the filter, false to RUN it
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        
        // Skip static resources (CSS, JS, images, fonts, etc.)
        if (path.startsWith("/assets/") || path.startsWith("/includes/")) {
            return true;
        }
        
        // Skip HTML files (static pages)
        if (path.endsWith(".html") && !path.startsWith("/api/")) {
            return true;
        }
        
        // Skip root path
        if (path.equals("/") || path.equals("")) {
            return true;
        }
        
        // Skip public auth endpoints (login, register)
        if (path.equals("/api/v1/auth/login") || path.equals("/api/v1/auth/register")) {
            return true;
        }
        
        // Process all other requests (including logout and authenticated API endpoints)
        return false;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        String authenticationHeader = request.getHeader("Authorization");

        // Only process if Authorization header is present
        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            String token = authenticationHeader.substring(7).trim();

            try {
                String username = utils.extractUsername.apply(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    
                    if (userDetails != null && utils.isTokenValid.apply(token, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                // Log error but don't block the request - let Spring Security handle it
                // Token validation failed, continue without authentication
            }
        }

        filterChain.doFilter(request, response);
    }
}