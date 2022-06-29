package com.practice.basexbackend.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.practice.basexbackend.constant.Security.OPTIONS_HTTP_METHOD;
import static com.practice.basexbackend.constant.Security.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Component @RequiredArgsConstructor @Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)) {
                response.setStatus(OK.value());
            } else {
                String authHeader = request.getHeader(AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
                    filterChain.doFilter(request, response);
                    return;
                }
                String token = authHeader.substring(TOKEN_PREFIX.length());
                String username = jwtTokenProvider.getSubject(token);
                if (jwtTokenProvider.isTokenValid(username, token) &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
                    Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    SecurityContextHolder.clearContext();
                }
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
