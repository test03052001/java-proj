package com.enterprise.platform.order.service;

import com.enterprise.platform.catalog.service.CatalogService;
import com.enterprise.platform.common.exception.BusinessException;
import com.enterprise.platform.common.exception.ResourceNotFoundException;
import com.enterprise.platform.inventory.service.InventoryService;
import com.enterprise.platform.order.domain.CustomerOrder;
import com.enterprise.platform.order.domain.OrderLine;
import com.enterprise.platform.order.domain.OrderStatus;
import com.enterprise.platform.order.repository.OrderRepository;
import com.enterprise.platform.order.web.dto.CreateOrderRequest;
import com.enterprise.platform.order.web.dto.OrderLineRequest;
import com.enterprise.platform.order.web.dto.OrderLineResponse;
import com.enterprise.platform.order.web.dto.OrderResponse;
import com.enterprise.platform.user.domain.User;
import com.enterprise.platform.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CatalogService catalogService;
    private final InventoryService inventoryService;

    @Transactional(readOnly = true)
    public List<OrderResponse> listForUser(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse get(Long id) {
        return orderRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    @Transactional
    public OrderResponse placeOrder(CreateOrderRequest request) {
        User user = userService.getEntity(request.userId());
        if (!user.isActive()) {
            throw new BusinessException("User is inactive");
        }

        CustomerOrder order = CustomerOrder.builder()
                .userId(user.getId())
                .status(OrderStatus.CREATED)
                .createdAt(Instant.now())
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;
        for (OrderLineRequest line : request.lines()) {
            var product = catalogService.getProductEntity(line.productId());
            if (!product.isActive()) {
                throw new BusinessException("Product inactive: " + product.getId());
            }
            inventoryService.reserveForOrder(product.getId(), line.quantity());
            BigDecimal unit = product.getUnitPrice();
            OrderLine ol = OrderLine.builder()
                    .order(order)
                    .productId(product.getId())
                    .quantity(line.quantity())
                    .unitPrice(unit)
                    .build();
            order.getLines().add(ol);
            total = total.add(unit.multiply(BigDecimal.valueOf(line.quantity())));
        }
        order.setTotalAmount(total);
        CustomerOrder saved = orderRepository.save(order);
        return toResponse(saved);
    }

    private OrderResponse toResponse(CustomerOrder o) {
        List<OrderLineResponse> lines = o.getLines().stream()
                .map(l -> new OrderLineResponse(
                        l.getProductId(),
                        l.getQuantity(),
                        l.getUnitPrice(),
                        l.getUnitPrice().multiply(BigDecimal.valueOf(l.getQuantity()))
                ))
                .toList();
        return new OrderResponse(o.getId(), o.getUserId(), o.getStatus(), o.getTotalAmount(), o.getCreatedAt(), lines);
    }
}
