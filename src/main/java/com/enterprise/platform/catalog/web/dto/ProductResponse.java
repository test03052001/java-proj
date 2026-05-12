package com.enterprise.platform.catalog.web.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String sku,
        String name,
        BigDecimal unitPrice,
        Long categoryId,
        String categoryName,
        boolean active
) {
}
