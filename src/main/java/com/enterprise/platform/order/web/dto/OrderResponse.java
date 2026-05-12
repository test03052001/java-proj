package com.enterprise.platform.order.web.dto;

import com.enterprise.platform.order.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        Long userId,
        OrderStatus status,
        BigDecimal totalAmount,
        Instant createdAt,
        List<OrderLineResponse> lines
) {
}
