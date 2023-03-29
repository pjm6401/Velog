package com.example.demo.Config;

import com.example.demo.Util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.filter.CorsFilter;

/*
    -------------------------
    class 설명
    -------------------------
    기본적인 Web 설정 및 PassWord Encoding
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig implements PasswordEncoder{

    private final CorsFilter corsFilter; 		// 추가한 코드
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    // CharSequence = string

    //Password Encode BCrypt ALG
    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }

    //Password matches
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
    // 암호화에 필요한 PasswordEncoder 를 Bean 등록 아래는 다양한 알고리즘으로 알아서 암호화 위와 택 1하면 된다
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //csrf 보안 토큰 비활성화, JWT 토큰을 사용할경우 필수적으로 사용
        http.csrf().disable();
        http.httpBasic().disable();
        http.authorizeHttpRequests().requestMatchers("/*").permitAll();
        //CORS 설정 메소드 체인
        //http.cors().and();
        /*http
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 안함
                .and()
                /*
                .authorizeRequests() → .authorizeHttpRequests()
                  .antMatchers() → .requestMatchers()
                 */
                //.authorizeHttpRequests()
                //.requestMatchers("/login").permitAll() // 토큰이없는경우 로그인기능만 이용 가능하도록 제한
                //.anyRequest().authenticated();
                //.and()
                // CorsConfig 에서 Bean으로 등록한 corsFilter를 기존 Filter보다 앞서서 수행하겠다는 의미
                //.addFilterBefore(corsFilter, SecurityContextHolderFilter.class)
                //.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                //.exceptionHandling()
                /*
                ExceptionHandlingConfigurer 객체에서 authenticationEntryPoint() 메서드를 호출하여,
                인증되지 않은 사용자가 보호된 리소스에 접근하려고 시도할 때, 401 Unauthorized HTTP 응답 코드를 반환하도록 설정하는 것.
                 */
                //.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        return http.build();
    }
}
