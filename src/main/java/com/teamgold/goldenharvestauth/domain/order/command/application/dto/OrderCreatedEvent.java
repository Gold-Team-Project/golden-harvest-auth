package com.teamgold.goldenharvestauth.domain.order.command.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreatedEvent {
    private String productId;
    private Integer quantity;
    private String userEmail; // 주문한 사용자 정보
}
