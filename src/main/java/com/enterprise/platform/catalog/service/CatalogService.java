package com.enterprise.platform.catalog.service;

import com.enterprise.platform.catalog.domain.Category;
import com.enterprise.platform.catalog.domain.Product;
import com.enterprise.platform.catalog.repository.CategoryRepository;
import com.enterprise.platform.catalog.repository.ProductRepository;
import com.enterprise.platform.catalog.web.dto.CategoryResponse;
import com.enterprise.platform.catalog.web.dto.ProductRequest;
import com.enterprise.platform.catalog.web.dto.ProductResponse;
import com.enterprise.platform.common.exception.BusinessException;
import com.enterprise.platform.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> listCategories() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> listProducts(Long categoryId) {
        List<Product> list = categoryId == null
                ? productRepository.findByActiveTrue()
                : productRepository.findByCategoryId(categoryId);
        return list.stream().map(this::toProductResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        return toProductResponse(p);
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.findBySku(request.sku()).isPresent()) {
            throw new BusinessException("SKU already exists");
        }
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + request.categoryId()));
        Product product = Product.builder()
                .sku(request.sku())
                .name(request.name())
                .unitPrice(request.unitPrice())
                .category(category)
                .active(true)
                .build();
        return toProductResponse(productRepository.save(product));
    }

    public Product getProductEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    private ProductResponse toProductResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getSku(),
                p.getName(),
                p.getUnitPrice(),
                p.getCategory().getId(),
                p.getCategory().getName(),
                p.isActive()
        );
    }
}
