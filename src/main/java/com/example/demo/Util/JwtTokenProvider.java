package com.example.demo.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
/*
    -------------------------
    class 설명
    -------------------------
    JWT 토큰 발급 class
 */

@Component
public class JwtTokenProvider {
    private Logger Logger = LogManager.getLogger(this.getClass());
    private String secretKey = "236979CB6F1AD6B6A6184A31E6BE37DB3818CC36871E26235DD67DCFE4041492";
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    /*
    io.jsonwebtoken.security.Keys 클래스의 hmacShaKeyFor() 메서드를 이용하여,
    문자열 secretKey를 기반으로 HMAC-SHA 알고리즘에 사용될 수 있는 javax.crypto.SecretKey 객체를 생성.
     */

    // 토큰 유효시간 30분
    private final long tokenValidTime = 30 * 60 * 1000L;

   /* private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }*/

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userPk, String roles) {
        System.out.println(key);
        Claims claims = Jwts.claims();
        System.out.println("userPK: "+userPk);
        System.out.println(roles);
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("ID", userPk);
        payloads.put("roles", roles);
        Date now = new Date();
        Logger.info("Create Token");
        return Jwts.builder()
                .setSubject("JWT AUTH")
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(payloads) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(key,SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    /*public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }*/

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        //return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}