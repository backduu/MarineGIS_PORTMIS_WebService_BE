package com.bluespace.marine_mis_service.Component;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * <pre>
 * ===========================================================
 * Program Name : JwtTokenProvider
 * Description  : 토큰 제공자 관련 설정 소스
 * Author       : 백두현
 * Create Date  : 2026-02-23
 *
 * 변경이력
 * -----------------------------------------------------------
 * 2026-02-23  백두현  최초작성
 * ===========================================================
 * </pre>
 */
@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long validityInMilliseconds = 1000L * 60 * 60; // 1시간
    private final long resetTokenValidity = 1000L * 60 * 30; // 30분

    public JwtTokenProvider() {
        // 비밀키를 프론트에서 받는다면 해당 키를 복호화한다.
        String base64Secret = "abc.def";
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    }

    // 토큰 생성
    public String createToken(String email, String role, String username, Map<String, Object> extraClaims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMilliseconds); // 토큰 30분 유효기한

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("type", "reset_token");

        if(extraClaims != null & !extraClaims.isEmpty()) {
            claims.putAll(extraClaims);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 서명 검증용 키 설정
                    .build()
                    .parseClaimsJwt(token); // 파싱 시 예외 발생 여부로 유효성 판단
            return true;
        } catch (Exception e) {
            return false; // 서명 불일치, 만료 등 예외 발생 시 false
        }
    }

    // 리셋 토큰 검증
    public boolean validateResetToken(String token, String expectedEmail) {
        try {
            Claims claims = getClaimsFromToken(token);
            String email = claims.getSubject();
            String type = claims.get("type", String.class);
            Date expiryDate = claims.getExpiration();

            return email.equals(expectedEmail)
                    && type.equals("reset_token")
                    && expiryDate.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 만료 여부 확인
    public boolean isTokenExpired(String token) {
        try {
            Date expiryDate = getClaimsFromToken(token).getExpiration();
            return expiryDate.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    // 토큰에서 Claims 전체 추출
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰에서 이메일(subject) 추출
    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // 토큰에서 role 추출
    public String getRoleFromToken(String token) {
        return getClaimsFromToken(token).get("role", String.class);
    }

    // 토큰에서 username 추출
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).get("username", String.class);
    }

    // 토큰에서 type 추출
    public String getTypeFromToken(String token) {
        return getClaimsFromToken(token).get("type", String.class);
    }
}
