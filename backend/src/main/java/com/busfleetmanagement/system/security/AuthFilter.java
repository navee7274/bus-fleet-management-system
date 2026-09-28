package com.busfleetmanagement.system.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final SessionManager sessionManager;

    public AuthFilter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // Allow login without authentication
        if (requestURI.equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String sessionId = getSessionIdFromCookie(request);

        // No session cookie
        if (sessionId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"message\":\"Authentication required\"}"
            );
            return;
        }

        // Session doesn't exist
        Integer ownerId = sessionManager.getOwnerId(sessionId);

        if (ownerId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"message\":\"Invalid or expired session\"}"
            );
            return;
        }

        // Store the authenticated owner ID for the rest of the request
        request.setAttribute("ownerId", ownerId);

        // Authentication successful
        filterChain.doFilter(request, response);
    }

    private String getSessionIdFromCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {

            if ("SESSION_ID".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }
}


