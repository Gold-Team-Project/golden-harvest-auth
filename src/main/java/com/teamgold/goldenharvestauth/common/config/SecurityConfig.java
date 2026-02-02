package com.teamgold.goldenharvestauth.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity; // 리액티브 보안
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain; // 리액티브 필터 체인
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource; // 리액티브 CORS
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource; // 리액티브 CORS

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // CSRF 비활성화 (API Gateway에서는 일반적으로 비활성화)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정 적용
                .authorizeExchange(auth -> auth // 리액티브 인증/인가 규칙 설정
                        // 인증이 필요 없는 공개 경로 설정
                        .pathMatchers("/api/auth/**").permitAll()
                        // 예시: /api/admin/** 경로는 ADMIN 역할만 허용
                        // .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        // 그 외 모든 요청은 인증 필요
                        .anyExchange().authenticated()
                );

        // 참고: GatewayAuthenticationFilter는 GlobalFilter이므로 Spring Cloud Gateway에 의해 자동으로 감지되어 적용됩니다.
        //       ServerHttpSecurity 필터 체인에 명시적으로 추가할 필요가 없습니다.
        
        return http.build();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        // 허용할 Origin 패턴을 추가 (프론트엔드 URL 등)
//        config.setAllowedOriginPatterns(List.of("http://localhost:5173", "http://localhost:8080", "http://your-frontend-domain.com"));
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
//        config.setAllowedHeaders(List.of("*"));
//        config.addExposedHeader("Authorization"); // 클라이언트에서 접근 가능한 헤더
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
}