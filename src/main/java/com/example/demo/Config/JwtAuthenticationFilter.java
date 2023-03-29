package com.example.demo.Config;
import com.example.demo.Util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
/*
    -------------------------
    class 설명
    -------------------------
    JWT 토큰의 유효성 여부를 Controller 에
    도착하기 전에 거르는 Filter
    GenericFilterBean 를 상속받아 서블릿 필터를 구현
 */

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("JWTfilter");
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        // 헤더에서 JWT 를 받기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        String splitToken = null;
        // Front 에서 JWT 토큰 앞에 Bearer 라는 문자열을 추가해서 오기때문에 분리가 필요하다.
        if(token!= null) {
             splitToken= token.substring("Bearer ".length());
        }
        logger.info("JWT TOKEN : "+ splitToken);
        // 유효한 토큰인지 확인
        if (token != null && jwtTokenProvider.validateToken(splitToken)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받기.
            // 주석 풀어야함 Authentication authentication = jwtTokenProvider.getAuthentication(splitToken);
            // SecurityContext 에 Authentication 객체를 저장.
            // SecurityContextHolder는 Spring Security에서 인증된 사용자의 보안 컨텍스트 정보를 저장하고 관리하는 클래스
            // SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Security context에 인증 정보를 저장했습니다, uri: {}", requestURI);
        }else{
            logger.debug("유효한 Jwt 토큰이 없습니다, uri: {}", requestURI);
        }
        chain.doFilter(request, response);
    }
}
