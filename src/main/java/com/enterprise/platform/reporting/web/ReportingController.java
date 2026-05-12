package com.enterprise.platform.reporting.web;

import com.enterprise.platform.catalog.repository.CategoryRepository;
import com.enterprise.platform.catalog.repository.ProductRepository;
import com.enterprise.platform.common.api.ApiResponse;
import com.enterprise.platform.order.repository.OrderRepository;
import com.enterprise.platform.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reporting")
@RequiredArgsConstructor
@Tag(name = "Reporting", description = "Lightweight operational counts")
public class ReportingController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/summary")
    @Operation(summary = "Entity counts for dashboards")
    public ApiResponse<Map<String, Long>> summary() {
        return ApiResponse.ok(Map.of(
                "users", userRepository.count(),
                "products", productRepository.count(),
                "categories", categoryRepository.count(),
                "orders", orderRepository.count()
        ));
    }
}
