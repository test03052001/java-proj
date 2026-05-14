package com.enterprise.platform.catalog.service;

import com.enterprise.platform.catalog.domain.Category;
import com.enterprise.platform.catalog.domain.Product;
import com.enterprise.platform.catalog.repository.CategoryRepository;
import com.enterprise.platform.catalog.repository.ProductRepository;
import com.enterprise.platform.catalog.web.dto.CategoryRequest;
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

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        categoryRepository.findByNameIgnoreCase(request.name())
                .ifPresent(c -> {
                    throw new BusinessException("Category already exists: " + request.name());
                });
        Category category = categoryRepository.save(Category.builder().name(request.name()).build());
        return toCategoryResponse(category);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        categoryRepository.findByNameIgnoreCase(request.name())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException("Category already exists: " + request.name());
                });
        category.setName(request.name());
        return toCategoryResponse(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        if (productRepository.countByCategoryId(id) > 0) {
            throw new BusinessException("Category has products and cannot be deleted");
        }
        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> listProducts(Long categoryId) {
        List<Product> list = categoryId == null
                ? productRepository.findByActiveTrue()
                : productRepository.findByCategoryId(categoryId);
        return list.stream().map(this::toProductResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> searchProducts(String term) {
        return productRepository.findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(term, term).stream()
                .map(this::toProductResponse)
                .toList();
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

    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        if (productRepository.existsBySkuAndIdNot(request.sku(), id)) {
            throw new BusinessException("SKU already exists");
        }
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + request.categoryId()));
        product.setSku(request.sku());
        product.setName(request.name());
        product.setUnitPrice(request.unitPrice());
        product.setCategory(category);
        product.setActive(true);
        return toProductResponse(product);
    }

    @Transactional
    public void deactivateProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        product.setActive(false);
    }

    public Product getProductEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    private CategoryResponse toCategoryResponse(Category c) {
        return new CategoryResponse(c.getId(), c.getName());
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
