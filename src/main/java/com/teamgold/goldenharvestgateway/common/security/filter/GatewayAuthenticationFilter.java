package com.teamgold.goldenharvestgateway.common.security.filter;

import com.teamgold.goldenharvestgateway.common.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class GatewayAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtTokenProvider jwtTokenProvider;

    // 인증을 요구하지 않는 public 경로 목록
    private final List<String> publicRoutes = List.of(
            "/api/auth/login",
            "/api/auth/signup",
            "/api/auth/reissue"
            // 필요하다면 다른 public 경로를 여기에 추가
    );

    @Autowired
    public GatewayAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public int getOrder() {
        // 필터 우선순위를 가장 높게 설정하여 다른 필터보다 먼저 실행되도록 함
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 요청 경로가 public route에 해당하면 필터를 적용하지 않고 통과
        if (isPublicRoute(request.getURI().getPath())) {
            return chain.filter(exchange);
        }

        String token = resolveToken(request);

        if (token == null) {
            return onError(exchange, "Authorization header is missing", HttpStatus.UNAUTHORIZED);
        }

        if (!jwtTokenProvider.validateToken(token)) {
            return onError(exchange, "Token is not valid", HttpStatus.UNAUTHORIZED);
        }

        // 토큰에서 사용자 정보 추출
        Claims claims = jwtTokenProvider.getClaims(token);
        String userEmail = claims.getSubject(); // 'subject'는 이메일을 담고 있음
        String role = claims.get("role").toString(); // 'role' 클레임 추출

        // 요청 헤더에 사용자 정보 추가
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-USER-EMAIL", userEmail) // 헤더 이름을 명확하게 변경
                .header("X-USER-ROLE", role)
                .build();

        // 수정된 요청으로 다음 필터 체인을 계속 진행
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private boolean isPublicRoute(String path) {
        return publicRoutes.stream().anyMatch(path::startsWith);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(errorMessage);
        return response.setComplete();
    }
}
