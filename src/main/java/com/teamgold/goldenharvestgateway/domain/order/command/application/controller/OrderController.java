package com.teamgold.goldenharvestgateway.domain.order.command.application.controller;

import com.teamgold.goldenharvestgateway.common.kafka.EventProducerService;
import com.teamgold.goldenharvestgateway.domain.order.command.application.dto.CreateOrderRequest;
import com.teamgold.goldenharvestgateway.domain.order.command.application.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final EventProducerService eventProducerService;

    @PostMapping
    public Mono<ResponseEntity<Void>> createOrder(
            @RequestHeader("X-USER-EMAIL") String userEmail,
            @RequestBody CreateOrderRequest request) {

        // Kafka로 보낼 이벤트 객체 생성
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .userEmail(userEmail)
                .build();

        // Kafka 토픽으로 이벤트 발행
        eventProducerService.send("order-created-topic", event);

        // 클라이언트에게는 요청이 접수되었다는 응답을 즉시 반환
        return Mono.just(ResponseEntity.accepted().build());
    }
}
