package com.enterprise.platform.inventory.web;

import com.enterprise.platform.common.api.ApiResponse;
import com.enterprise.platform.inventory.service.InventoryService;
import com.enterprise.platform.inventory.web.dto.StockAdjustmentRequest;
import com.enterprise.platform.inventory.web.dto.StockResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory", description = "Stock levels and adjustments")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/stocks")
    @Operation(summary = "List all stock levels from DB")
    public ApiResponse<List<StockResponse>> listStock() {
        return ApiResponse.ok(inventoryService.listStock());
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "Available quantity for SKU")
    public ApiResponse<StockResponse> getByProduct(@PathVariable Long productId) {
        int qty = inventoryService.getAvailable(productId);
        return ApiResponse.ok(new StockResponse(productId, qty));
    }

    @PutMapping("/products/{productId}/adjust")
    @Operation(summary = "Apply delta to on-hand (warehouse)")
    public ApiResponse<StockResponse> adjust(
            @PathVariable Long productId,
            @RequestParam int delta
    ) {
        inventoryService.adjustStock(productId, delta);
        return ApiResponse.ok(new StockResponse(productId, inventoryService.getAvailable(productId)));
    }

    @PutMapping("/products/set")
    @Operation(summary = "Set absolute on-hand quantity")
    public ApiResponse<StockResponse> setAbsolute(@Valid @RequestBody StockAdjustmentRequest body) {
        int current = inventoryService.getAvailable(body.productId());
        int delta = body.quantityOnHand() - current;
        inventoryService.adjustStock(body.productId(), delta);
        return ApiResponse.ok(new StockResponse(body.productId(), inventoryService.getAvailable(body.productId())));
    }
}
