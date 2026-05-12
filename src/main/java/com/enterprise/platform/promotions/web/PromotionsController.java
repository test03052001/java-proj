package com.enterprise.platform.promotions.web;

import com.enterprise.platform.common.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/promotions")
@Tag(name = "Promotions", description = "Campaigns and coupons (stub)")
public class PromotionsController {

    @GetMapping("/active")
    @Operation(summary = "Active storefront promotions")
    public ApiResponse<List<Map<String, Object>>> active() {
        return ApiResponse.ok(List.of());
    }
}
