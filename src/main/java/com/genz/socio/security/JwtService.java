package com.genz.socio.security;

import com.genz.socio.dto.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private Long expire;

    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getUserName())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String authorization){
        return getClaims(authorization).getSubject();
    }

    public boolean isTokenValid(String athorization, UserDetails userDetails){
        return extractUserName(athorization).equals(userDetails.getUsername()) && !isExpiredToken(athorization);
    }

    private boolean isExpiredToken(String authorization) {
        return getClaims(authorization).getExpiration().before(new Date());
    }

    private Claims getClaims(String authorization) {
        authorization = (authorization != null && authorization.startsWith("Bearer ")) ? authorization.substring(7) : authorization;
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(authorization)
                .getBody();
    }
}
