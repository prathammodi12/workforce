package com.workforce.tracker.auth.filter;


import com.workforce.tracker.auth.security.CustomUserDetailsService;
import com.workforce.tracker.auth.util.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)  request;

        String authHeader= req.getHeader("Authorization");

        System.out.println("Incoming Request: " + ((HttpServletRequest) request).getRequestURI());

        if(authHeader !=null && authHeader.startsWith("Bearer")){
            String token = authHeader.substring(7);

            try{
                String username= jwtUtil.extractUsername(token);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authenticated User: "+username);
                System.out.println("Roles: " + userDetails.getAuthorities());

            } catch (Exception e) {
                System.out.println("Invalid Token");
            }
        }

        chain.doFilter(request,response);
    }
}
