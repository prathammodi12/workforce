package com.workforce.tracker.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private  final String SECRET= "secret_key";

    //Generate JWT token
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))// 1hour
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    //Extract username from token
    public String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //  Validate token
    public boolean validateToken(String token, String username){
        return extractUsername(token).equals(username);
    }
}
