package com.leechee.utils;

import java.util.Date;
import java.util.Map;
import java.nio.charset.StandardCharsets;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
    
    /**
     * 使用HS256算法和固定密钥生成jwt
     * @param secretKey
     * @param ttlMills
     * @param claims
     * @return
     */
    public static String createJWT(String secretKey, long ttlMills, Map<String, Object> claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long expMills = System.currentTimeMillis() + ttlMills;

        Date exp = new Date(expMills);
        
        JwtBuilder builder = Jwts.builder()
               .setClaims(claims)
               .setExpiration(exp)
               .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8));
        
        return builder.compact();
    }

    /**
     * 解析JWT
     * @param secretKey
     * @param token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        Claims claims = Jwts.parser()
               .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
               .parseClaimsJws(token)
               .getBody();
        return claims;
    }

}
