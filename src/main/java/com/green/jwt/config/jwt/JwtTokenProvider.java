package com.green.jwt.config.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.jwt.config.JwtConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/*
    Access Token, Refresh Token - 로그인 때 발행
    ar - 로그인 인증처리(인증 유효 시간을 짧게 가져간다.)
         응답 body에 담는다.
    rt - ar 재발행(인증 유효 시간을 길게 가져간다.)
         쿠키(http-only)에 담는다.

    JWT 구조
    : 1.2.3
      header.payload(claim).signature
 */

@Slf4j
@Component
public class JwtTokenProvider {
    private final ObjectMapper objectMapper; // Jackson 라이브러리
    private final JwtConst jwtConst;
    private final SecretKey secretKey;

    public JwtTokenProvider(ObjectMapper objectMapper, JwtConst jwtConst) {
        this.objectMapper = objectMapper;
        this.jwtConst = jwtConst;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConst.getSecret()));
    }

    public String generateAccessToken(JwtUser jwtUser) {
        return generateToken(jwtUser, jwtConst.getAccessTokenExpiry());
    }

    public String generateRefreshToken(JwtUser jwtUser) {
        return generateToken(jwtUser, jwtConst.getRefreshTokenExpiry());
    }

    public String generateToken(JwtUser jwtUser, long tokenValidMilliSecond) {
        Date now = new Date();
        return Jwts.builder()
                .header().type(jwtConst.getTokenName()) //header
                .and()

                .issuer(jwtConst.getIssuer()) //payload
                .issuedAt(now) //payload
                .expiration(new Date(now.getTime() + tokenValidMilliSecond)) //payload
                .claim(jwtConst.getClaimKey(), makeClaimByUserToString(jwtUser)) //payload

                .signWith(secretKey) //signature
                .compact(); //합치기
    }

    // 객체 > String : 직렬화(JSON)
    private String makeClaimByUserToString(JwtUser jwtUser) {
        // 객체 자체를 JWT에 담고 싶어서 객체를 직렬화(여기서는 객체를 String으로 바꾸는 작업)
        // jwtUser에 담고 있는 데이터를 JSON 형태의 문자열로 변환 - 직렬화
        try {
            return objectMapper.writeValueAsString(jwtUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //----------- 만들어진 토큰(at, rt)
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public JwtUser getJwtUserFromToken(String token) {
        Claims claims = getClaims(token);
        String json = claims.get(jwtConst.getClaimKey(), String.class);
        try {
            return objectMapper.readValue(json, JwtUser.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Authentication getAuthentication(String token) {
        JwtUser jwtUser = getJwtUserFromToken(token);
        return new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
    }
}
