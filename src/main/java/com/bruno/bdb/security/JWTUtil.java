package com.bruno.bdb.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-time}")
    private Long expirationTime;

    public String generateToken(String id) {
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public boolean tokenIsValid(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String id = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            return id != null && expirationDate != null && now.before(expirationDate);
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("Expired token: " + token);
            throw new RuntimeException(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new AuthenticationServiceException(ex.getMessage());
        }
    }

    public String getId(String token) {
        Claims claims = getClaims(token);
        if (claims != null) return claims.getSubject();
        return null;
    }
}