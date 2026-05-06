package com.workforce.tracker.auth.filter;


import com.workforce.tracker.auth.security.CustomUserDetailsService;
import com.workforce.tracker.auth.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)  request;

        String authHeader= req.getHeader("Authorization");

        log.info("Incoming request: {}", req.getRequestURI());;

        if(authHeader !=null && authHeader.startsWith("Bearer")){
            String token = authHeader.substring(7); // remove Bearer

            try{
                String username= jwtUtil.extractUserName(token);
                String role= jwtUtil.extractRole(token);

                log.info("User: {}, Role: {}",username,role);

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+role);
                // Spring expects ROLE_ prefix

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username, //principal
                                null, // no password needed here
                                Collections.singletonList(authority));

                SecurityContextHolder.getContext().setAuthentication(auth);
                // tells Spring: user is authenticated

            } catch (Exception e) {
                log.error("Invalid JWT Token{}", e.getMessage());
            }
        }

        chain.doFilter(request,response);
        // continue request to controller
    }
}
