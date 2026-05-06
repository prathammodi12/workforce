package com.workforce.tracker.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workforce.tracker.common.response.CommonResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");

        CommonResponse<Object> errorResponse =
                CommonResponse.failure("Unauthorized");

        ObjectMapper mapper = new ObjectMapper();

        response.getWriter().write(
                mapper.writeValueAsString(errorResponse)
        );
    }
}
