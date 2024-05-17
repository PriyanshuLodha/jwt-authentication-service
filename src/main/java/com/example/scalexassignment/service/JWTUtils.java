package com.example.scalexassignment.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;
@Service
public class JWTUtils {
    private SecretKey key;
    private static final long EXPIRATION_TIME=86400000; //24 hours in milliseconds
    public JWTUtils() {
        byte[] keyBytes = new byte[32]; // 256-bit key for HmacSHA256
        new SecureRandom().nextBytes(keyBytes);
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
    public String generateRefreshToken(HashMap<String, Objects> claims,UserDetails userDetails){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }
    private <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
    }
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUserName(token);
        return (username.equals(userDetails.getUsername())&&!isTokenExpiry(token));
    }
    public boolean isTokenExpiry(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }
}
