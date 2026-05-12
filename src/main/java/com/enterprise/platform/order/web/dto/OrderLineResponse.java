package com.enterprise.platform.order.web.dto;

import java.math.BigDecimal;

public record OrderLineResponse(Long productId, int quantity, BigDecimal unitPrice, BigDecimal lineTotal) {
}
