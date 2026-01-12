package com.ofektom.med.utils;


import com.ofektom.med.serviceImpl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
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
        
        String path = request.getRequestURI();
        String method = request.getMethod();
        String authenticationHeader = request.getHeader("Authorization");

        logger.debug("JWT Filter - Processing request: {} {}", method, path);

        // Only process if Authorization header is present
        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            String token = authenticationHeader.substring(7).trim();
            logger.debug("JWT Filter - Authorization header found, token length: {}", token.length());

            try {
                String username = utils.extractUsername.apply(token);
                logger.debug("JWT Filter - Extracted username: {}", username);

                Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
                if (username != null && existingAuth == null) {
                    logger.debug("JWT Filter - No existing authentication, loading user details for: {}", username);
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    
                    if (userDetails != null) {
                        boolean isValid = utils.isTokenValid.apply(token, userDetails.getUsername());
                        logger.debug("JWT Filter - Token valid: {}, User authorities: {}", isValid, userDetails.getAuthorities());
                        
                        if (isValid) {
                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                            logger.info("JWT Filter - Authentication set successfully for user: {} with roles: {}", 
                                    username, userDetails.getAuthorities());
                        } else {
                            logger.warn("JWT Filter - Token validation failed for user: {}", username);
                        }
                    } else {
                        logger.warn("JWT Filter - UserDetails is null for username: {}", username);
                    }
                } else if (existingAuth != null) {
                    logger.debug("JWT Filter - Authentication already exists: {}", existingAuth.getName());
                }
            } catch (Exception e) {
                logger.error("JWT Filter - Exception during authentication for path: {}", path, e);
                // Continue without authentication - let Spring Security handle it
            }
        } else {
            logger.debug("JWT Filter - No Authorization header found for path: {}", path);
        }

        // Log authentication status before proceeding
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.debug("JWT Filter - SecurityContext has authentication: {} with authorities: {}", 
                    auth.getName(), auth.getAuthorities());
        } else {
            logger.debug("JWT Filter - SecurityContext has no authentication for path: {}", path);
        }

        filterChain.doFilter(request, response);
    }
}