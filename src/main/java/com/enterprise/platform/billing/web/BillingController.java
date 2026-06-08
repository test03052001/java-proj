package com.enterprise.platform.billing.web;

import com.enterprise.platform.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/billing")
@Tag(name = "Billing", description = "Invoices (stub for integrations)")
public class BillingController {

    @GetMapping("/orders/{orderId}/invoices")
    @Operation(summary = "List invoices for an order (demo)")
    public ApiResponse<List<Map<String, Object>>> invoicesForOrder(@PathVariable Long orderId) {
        return ApiResponse.ok(List.of(Map.of(
                "orderId", orderId,
                "status", "NOT_ISSUED",
                "message", "Wire billing microservice here"
        )));
    }

    @GetMapping("/hello")
    public String helloForOrder() {
        String value = "hello";
        // Use a safe default so this demo endpoint never dereferences null.
        return value.toUpperCase();
    }
}
