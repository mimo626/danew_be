package com.example.danew_spring;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey SECRET_KEY;

    @Value("${jwt.expiration}")
    private String expirationTimeStr;

    private long EXPIRATION_TIME;

    @Value("${jwt.refresh-expiration}")
    private String refreshExpirationTimeStr;

    private long REFRESH_EXPIRATION_TIME;

    @PostConstruct
    public void init() {
        EXPIRATION_TIME = Long.parseLong(expirationTimeStr);
        REFRESH_EXPIRATION_TIME = Long.parseLong(refreshExpirationTimeStr);
        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Access Token 생성
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // 토큰에서 userId 추출
    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Access Token 유효성 검증 + 만료 시 Refresh로 재발급
    public String validateAndRefreshToken(String token, String refreshToken) {
        try {
            // Access Token 정상 유효
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return token; // 아직 만료 안 됨
        } catch (ExpiredJwtException e) {
            // Access Token 만료 → Refresh Token 확인
            if (validateToken(refreshToken)) {
                String userId = getUserIdFromToken(refreshToken);
                return generateToken(userId); // 새 Access Token 발급
            } else {
                throw new RuntimeException("Refresh Token 만료됨, 다시 로그인 필요");
            }
        } catch (Exception e) {
            throw new RuntimeException("유효하지 않은 토큰");
        }
    }

    // Refresh Token 포함 모든 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

