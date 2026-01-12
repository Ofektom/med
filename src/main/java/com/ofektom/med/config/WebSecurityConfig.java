package com.ofektom.med.config;

import com.ofektom.med.serviceImpl.UserServiceImpl;
import com.ofektom.med.utils.JwtAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    
    private final UserServiceImpl userService;
    private final JwtAuthenticationFilter authentication;

    @Autowired
    public WebSecurityConfig(@Lazy UserServiceImpl userService, JwtAuthenticationFilter authentication) {
        this.userService = userService;
        this.authentication = authentication;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // For local testing
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(username -> userService.loadUserByUsername(username));
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/*.html",           // Allow all HTML files in the root of static/
                                "/assets/**",        // Allow all files in assets/
                                "/includes/**"       // Allow all files in includes/
                        ).permitAll()
                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/register").permitAll()
                        .requestMatchers(
                                "/api/v1/users/**",
                                "/api/v1/medication/**",
                                "/api/v1/assets/**",
                                "/api/v1/rooms/**",
                                "/api/v1/search/**").authenticated()
                        .requestMatchers("/api/v1/auth/add", "/api/v1/auth/logout").authenticated()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            String path = request.getRequestURI();
                            String method = request.getMethod();
                            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                            
                            String userInfo = "anonymous";
                            String authorities = "none";
                            if (auth != null) {
                                userInfo = auth.getName();
                                authorities = auth.getAuthorities().toString();
                            }
                            
                            logger.error("ACCESS DENIED - Method: {}, Path: {}, User: {}, Authorities: {}, Exception: {}", 
                                    method, path, userInfo, authorities, accessDeniedException.getMessage());
                            
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            try {
                                Map<String, Object> errorResponse = new HashMap<>();
                                errorResponse.put("statusCode", 403);
                                errorResponse.put("message", "You do not have permission to perform this action.");
                                errorResponse.put("path", path);
                                errorResponse.put("method", method);
                                errorResponse.put("requiredRoles", "ROLE_SUPER_ADMIN or ROLE_ADMIN");
                                if (auth != null) {
                                    errorResponse.put("userRoles", authorities);
                                }
                                
                                ObjectMapper mapper = new ObjectMapper();
                                response.getWriter().write(mapper.writeValueAsString(errorResponse));
                            } catch (IOException e) {
                                logger.error("Error writing access denied response", e);
                                try {
                                    response.getWriter().write("{\"statusCode\":403,\"message\":\"Access Denied\"}");
                                } catch (IOException ex) {
                                    logger.error("Failed to write fallback error response", ex);
                                }
                            }
                        })
                )
                .logout(logout -> logout
                        .deleteCookies("remove")
                        .invalidateHttpSession(true)
                        .logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessUrl("/index.html")
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authentication, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}