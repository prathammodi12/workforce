package com.workforce.tracker.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private  final String SECRET= "secret_key";
    // in real production -> use ENV variable, not hardcoded

    //Generate JWT token
    public String generateToken(String username, String role){

        Map<String, Object> claims  = new HashMap<>();
        claims.put("role", role);
        //Store role inside token -> avoid DB call later

        return Jwts.builder()
                .setClaims(claims) //attach custom data
                .setSubject(username)// username
                .setIssuedAt(new Date()) //creation time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))// 1hour

                .signWith(SignatureAlgorithm.HS256, SECRET)
                //sign token to prevent temporing

                .compact(); //build JWT String
    }


    public String extractUserName(String token){
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
        // custom claim → role
    }


    //Extract username from token
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    //  Validate token
    public boolean validateToken(String token, String username){
        return extractUserName(token).equals(username) && !isTokenExpired(token);
    }

    // Check expiration
    private boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
