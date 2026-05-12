package com.enterprise.platform.catalog.web;

import com.enterprise.platform.common.api.ApiResponse;
import com.enterprise.platform.catalog.service.CatalogService;
import com.enterprise.platform.catalog.web.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog/categories")
@RequiredArgsConstructor
@Tag(name = "Catalog — Categories", description = "Product taxonomy")
public class CategoryController {

    private final CatalogService catalogService;

    @GetMapping
    @Operation(summary = "List categories")
    public ApiResponse<List<CategoryResponse>> list() {
        return ApiResponse.ok(catalogService.listCategories());
    }
}
