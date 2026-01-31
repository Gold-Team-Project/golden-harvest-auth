package com.teamgold.goldenharvestgateway.domain.order.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderRequest {
    private String productId;
    private Integer quantity;
}
