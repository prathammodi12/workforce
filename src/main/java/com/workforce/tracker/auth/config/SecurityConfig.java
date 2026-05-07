package com.workforce.tracker.auth.config;

import com.workforce.tracker.auth.filter.JwtFilter;
import com.workforce.tracker.auth.security.JwtAccessDeniedHandler;
import com.workforce.tracker.auth.security.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(
            JwtFilter jwtFilter,
            JwtAuthenticationEntryPoint authenticationEntryPoint,
            JwtAccessDeniedHandler accessDeniedHandler
    ) {
        this.jwtFilter = jwtFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean// Security Filter Chain
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .csrf(csrf -> csrf.disable())
                //disable CSRF

                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // no HTTP session -> every request must carry JWT

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/refresh","/auth/logout").permitAll()
                        //login API is public

                        .anyRequest().authenticated()
                        //everything else requires authentication
                )

                .addFilterBefore(jwtFilter,
                    UsernamePasswordAuthenticationFilter.class);
                // run our JWT filter Before Spring's default auth filter

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } //secure hashing for passwords
}
