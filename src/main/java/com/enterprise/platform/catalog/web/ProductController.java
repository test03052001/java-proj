package com.enterprise.platform.catalog.web;

import com.enterprise.platform.common.api.ApiResponse;
import com.enterprise.platform.catalog.service.CatalogService;
import com.enterprise.platform.catalog.web.dto.ProductRequest;
import com.enterprise.platform.catalog.web.dto.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog/products")
@RequiredArgsConstructor
@Tag(name = "Catalog — Products", description = "SKU catalog")
public class ProductController {

    private final CatalogService catalogService;

    @GetMapping
    @Operation(summary = "List products (optional filter by category)")
    public ApiResponse<List<ProductResponse>> list(@RequestParam(required = false) Long categoryId) {
        return ApiResponse.ok(catalogService.listProducts(categoryId));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name or SKU")
    public ApiResponse<List<ProductResponse>> search(@RequestParam String term) {
        return ApiResponse.ok(catalogService.searchProducts(term));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product")
    public ApiResponse<ProductResponse> get(@PathVariable Long id) {
        return ApiResponse.ok(catalogService.getProduct(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create product")
    public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductRequest body) {
        return ApiResponse.ok(catalogService.createProduct(body));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public ApiResponse<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest body) {
        return ApiResponse.ok(catalogService.updateProduct(id, body));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft-deactivate product")
    public void deactivate(@PathVariable Long id) {
        catalogService.deactivateProduct(id);
    }
}
