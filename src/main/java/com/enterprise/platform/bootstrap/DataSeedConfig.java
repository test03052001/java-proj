package com.enterprise.platform.bootstrap;

import com.enterprise.platform.catalog.domain.Category;
import com.enterprise.platform.catalog.domain.Product;
import com.enterprise.platform.catalog.repository.CategoryRepository;
import com.enterprise.platform.catalog.repository.ProductRepository;
import com.enterprise.platform.inventory.domain.Stock;
import com.enterprise.platform.inventory.repository.StockRepository;
import com.enterprise.platform.user.domain.User;
import com.enterprise.platform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Instant;

@Configuration
@RequiredArgsConstructor
public class DataSeedConfig {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final UserRepository userRepository;

    @Bean
    ApplicationRunner seedReferenceData() {
        return args -> {
            if (categoryRepository.count() > 0) {
                return;
            }
            Category electronics = categoryRepository.save(Category.builder().name("Electronics").build());
            Category office = categoryRepository.save(Category.builder().name("Office").build());

            Product laptop = productRepository.save(Product.builder()
                    .sku("LAP-001")
                    .name("Pro Laptop 14")
                    .unitPrice(new BigDecimal("1899.99"))
                    .category(electronics)
                    .active(true)
                    .build());
            Product mouse = productRepository.save(Product.builder()
                    .sku("MOU-042")
                    .name("Wireless Mouse")
                    .unitPrice(new BigDecimal("49.99"))
                    .category(electronics)
                    .active(true)
                    .build());
            productRepository.save(Product.builder()
                    .sku("DSK-200")
                    .name("Standing Desk")
                    .unitPrice(new BigDecimal("599.00"))
                    .category(office)
                    .active(true)
                    .build());

            stockRepository.save(Stock.builder().productId(laptop.getId()).quantityOnHand(25).build());
            stockRepository.save(Stock.builder().productId(mouse.getId()).quantityOnHand(500).build());

            userRepository.save(User.builder()
                    .email("demo@example.com")
                    .displayName("Demo User")
                    .createdAt(Instant.now())
                    .active(true)
                    .build());
        };
    }
}
