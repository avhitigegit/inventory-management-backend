package com.hardware.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/*
 * =====================================================================
 * WHAT IS CORS? (Cross-Origin Resource Sharing)
 * =====================================================================
 * When your React frontend (running on http://localhost:5173) tries to
 * call your Spring Boot API (running on http://localhost:8080), the
 * browser blocks it by default because they are on different "origins"
 * (different ports = different origin).
 *
 * This is a browser security feature called the "Same-Origin Policy".
 * CORS is the standard way to tell the browser: "It is okay, I allow
 * requests from that frontend."
 *
 * Without this config, every API call from your React app will fail
 * with: "Access to fetch at 'http://localhost:8080' has been blocked
 * by CORS policy."
 *
 * This CorsConfig class solves that problem by adding a filter that
 * attaches the correct CORS headers to every response.
 * =====================================================================
 */

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        /*
         * ALLOWED ORIGINS
         * ---------------
         * These are the frontend URLs that are allowed to call our API.
         * During development we allow localhost on common frontend ports.
         *
         * Vite (React) default  → http://localhost:5173
         * Create React App       → http://localhost:3000
         *
         * IMPORTANT: Before going to production, replace these with your
         * actual deployed frontend URL, e.g. "https://yourdomain.com".
         * Never use "*" (allow all) in production when credentials are sent.
         */
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:3000");

        /*
         * ALLOWED HEADERS
         * ---------------
         * These are the HTTP request headers our API will accept.
         *
         * "*" means accept any header.
         * The most important ones we rely on are:
         *   - "Authorization" → carries the JWT token (Bearer <token>)
         *   - "Content-Type"  → tells the server the request body is JSON
         */
        config.addAllowedHeader("*");

        /*
         * ALLOWED METHODS
         * ---------------
         * These are the HTTP methods the browser is allowed to use.
         *
         * GET    → fetch data (read)
         * POST   → create new records
         * PUT    → update existing records
         * DELETE → remove records
         * OPTIONS→ preflight check (browser sends this automatically
         *           before every cross-origin request to ask permission)
         */
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");

        /*
         * ALLOW CREDENTIALS
         * -----------------
         * Set to true so the browser is allowed to send the
         * "Authorization" header (which carries the JWT token).
         * Without this, the browser strips the token from cross-origin
         * requests and your login will silently fail.
         */
        config.setAllowCredentials(true);

        /*
         * MAX AGE (Preflight cache)
         * -------------------------
         * Before every cross-origin request, the browser sends a quick
         * "OPTIONS" request (called a preflight) to ask: "Am I allowed?"
         * This setting tells the browser to cache that answer for 3600
         * seconds (1 hour), so it does not send a preflight before
         * every single API call — which would slow things down.
         */
        config.setMaxAge(3600L);

        /*
         * URL PATTERN
         * -----------
         * "/**" means apply this CORS configuration to ALL endpoints in
         * the application — /api/auth/**, /api/products/**, etc.
         */
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
