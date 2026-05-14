package com.enterprise.platform.order.web.dto;

import com.enterprise.platform.order.domain.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusRequest(
        @NotNull OrderStatus status
) {
}
