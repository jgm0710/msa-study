package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
public class GatewayFilterConfig {

    @Bean
    public RouteLocator gatewayRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        r -> r.path("/first-service/**") // first-service url pattern 으로 들어오는 request 를 ".uri" 에 해당하는 uri 로 redirect 함을 의미
                                // 라우팅 처리 시 추가할 request header, response header 를 정의할 수 있습니다.
                                .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                                        .addResponseHeader("first-response", "first-response-header"))
                                .uri("http://localhost:8081") //해당 uri 에 해당하는 인스턴스로 요청을 redirect cjfl
                )
                .route(
                        r -> r.path("/second-service/**")
                                .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                        .addResponseHeader("second-response", "second-response-header"))
                                .uri("http://localhost:8082")
                )
                .build();
    }
}
