package com.spring.security.dev.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtils {

    private static Claims extractClaims(String token, String key) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public static boolean isExpired(String token, String secretKey) {
        // expire timestamp를 반환
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        return expiredDate.before(new Date());
    }

    public static String generateToken(String userName, String key, long expireTimeMs) {
        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("userName", userName); // Claim : Token에 담는 정보

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact()
                ;
    }
}
