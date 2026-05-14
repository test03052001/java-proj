package com.enterprise.platform.inventory.service;

import com.enterprise.platform.common.exception.BusinessException;
import com.enterprise.platform.inventory.domain.Stock;
import com.enterprise.platform.inventory.repository.StockRepository;
import com.enterprise.platform.inventory.web.dto.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public int getAvailable(Long productId) {
        return stockRepository.findByProductId(productId).map(Stock::getQuantityOnHand).orElse(0);
    }

    @Transactional(readOnly = true)
    public List<StockResponse> listStock() {
        return stockRepository.findAll().stream()
                .map(s -> new StockResponse(s.getProductId(), s.getQuantityOnHand()))
                .toList();
    }

    @Transactional
    public void adjustStock(Long productId, int delta) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseGet(() -> stockRepository.save(Stock.builder()
                        .productId(productId)
                        .quantityOnHand(0)
                        .build()));
        int next = stock.getQuantityOnHand() + delta;
        if (next < 0) {
            throw new BusinessException("Insufficient stock for product " + productId);
        }
        stock.setQuantityOnHand(next);
    }

    @Transactional
    public void reserveForOrder(Long productId, int quantity) {
        Stock stock = stockRepository.findByProductId(productId)
                .orElseThrow(() -> new BusinessException("No stock record for product " + productId));
        if (stock.getQuantityOnHand() < quantity) {
            throw new BusinessException("Insufficient stock for product " + productId);
        }
        stock.setQuantityOnHand(stock.getQuantityOnHand() - quantity);
    }
}
