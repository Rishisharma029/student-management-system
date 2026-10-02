package com.example.studentmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ═══════════════════════════════════════════════════════════════
 *  CORS Configuration
 * ═══════════════════════════════════════════════════════════════
 *
 *  CORS = Cross-Origin Resource Sharing.
 *
 *  Without this config, your React app (running on port 5173)
 *  would be BLOCKED from calling your Spring Boot API (port 8080).
 *  Browsers enforce a "same-origin policy" — requests from a
 *  different origin are blocked by default.
 *
 *  This config tells Spring Boot:
 *  "Allow requests from http://localhost:5173" (the React dev server)
 *
 *  In production, you'd change allowedOrigins to your actual domain.
 * ═══════════════════════════════════════════════════════════════
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")          // Apply to all /api/* endpoints
                    .allowedOrigins(
                        "http://localhost:5173",         // Vite React dev server
                        "http://localhost:3000"          // Create React App (fallback)
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")                  // Allow any request headers
                    .allowCredentials(true)               // Allow cookies (needed for auth later)
                    .maxAge(3600);                        // Cache preflight for 1 hour
            }
        };
    }
}
