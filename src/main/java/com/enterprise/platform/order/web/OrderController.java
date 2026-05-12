package com.enterprise.platform.order.web;

import com.enterprise.platform.common.api.ApiResponse;
import com.enterprise.platform.order.service.OrderService;
import com.enterprise.platform.order.web.dto.CreateOrderRequest;
import com.enterprise.platform.order.web.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Checkout and order history")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "List orders for a user")
    public ApiResponse<List<OrderResponse>> listByUser(@RequestParam Long userId) {
        return ApiResponse.ok(orderService.listForUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order")
    public ApiResponse<OrderResponse> get(@PathVariable Long id) {
        return ApiResponse.ok(orderService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place order (reserves inventory)")
    public ApiResponse<OrderResponse> place(@Valid @RequestBody CreateOrderRequest body) {
        return ApiResponse.ok(orderService.placeOrder(body));
    }
}
