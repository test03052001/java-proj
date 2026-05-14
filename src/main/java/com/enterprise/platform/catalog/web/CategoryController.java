package com.enterprise.platform.catalog.web;

import com.enterprise.platform.common.api.ApiResponse;
import com.enterprise.platform.catalog.service.CatalogService;
import com.enterprise.platform.catalog.web.dto.CategoryRequest;
import com.enterprise.platform.catalog.web.dto.CategoryResponse;
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
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create category")
    public ApiResponse<CategoryResponse> create(@Valid @RequestBody CategoryRequest body) {
        return ApiResponse.ok(catalogService.createCategory(body));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category")
    public ApiResponse<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest body) {
        return ApiResponse.ok(catalogService.updateCategory(id, body));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete empty category")
    public void delete(@PathVariable Long id) {
        catalogService.deleteCategory(id);
    }
}
