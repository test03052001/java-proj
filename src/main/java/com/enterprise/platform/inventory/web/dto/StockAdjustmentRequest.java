package com.enterprise.platform.inventory.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockAdjustmentRequest(
        @NotNull Long productId,
        @NotNull @Min(0) Integer quantityOnHand
) {
}
