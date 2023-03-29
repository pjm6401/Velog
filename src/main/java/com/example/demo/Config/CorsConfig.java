package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/*
    -------------------------
    class 설명
    -------------------------
    React - port : 3000
    Spring Boot - port : 8080
    통신을 위한 CORS 설정 파일
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        /*UrlBasedCorsConfigurationSource는 Spring Security 프레임워크에서 제공하는 클래스로,
        URL 패턴을 기반으로 웹 애플리케이션의 CORS(Cross-Origin Resource Sharing)를 구성할 수 있도록 해준다.*/
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 하위 메소드들을 구현하도록 제공하는 Spring Security 에서 제공한다.
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // 이 여부에 따라 CORS 접속 여부 판단
        //추후 허용하는 헤더와 메소드가 결정되면 수정 필요, 메소드와 헤더 허용에대한 코드
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.PUT);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.HEAD);
        //[Authorization : JWT TOKEN] React 에서 jwt 토큰을 Authorization 이라는 이름으로 header에 추가해서 보낸다.
        // 따라서 노출시킬 헤더는 Authorization을 노출시킨다. 이 이름으로 온 헤더를 노출시켜서 읽어야 권한인증 여부를 판단 가능하기 때문
        config.addExposedHeader("Authorization");
        /*
        source.registerCorsConfiguration(path,config) 형식
        지정한 Path에 위에 작성한 Corsconfig 를 설정한다는 의미
         */
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
