package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.function.Consumer;

@Component // bean 등록 필요
@Slf4j
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // spring WebFlux 를 사용하면서 HttpServletRequest 나 HttpServletResponse 가 아닌
            // ServerHttpRequest 를 사용
            // spring 5.x 부터는 RxJava 를 사용하여 리액티브 프로그래밍을 지원한다고 함.
            // 아직은 잘 모르지만, 비동기 처리 (및 이벤트 기반 프로그래밍) 를 하기 위해 사용하는 듯.
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();


            // request id logging
            log.info("Custom PRE filter : request id -> {}", request.getId());
            log.info("Request Uri : {}" , request.getURI());

            // Custom Post Filter
            // response status code logging
            return chain.filter(exchange).then(Mono.fromRunnable(() ->
                    log.info("Custom POST filter : response code -> {}", response.getStatusCode())));
        };
    }

    public static class Config {
        // Put the configuration properties
    }

}
