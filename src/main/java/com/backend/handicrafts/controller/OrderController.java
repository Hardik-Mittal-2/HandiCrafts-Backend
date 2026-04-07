package com.backend.handicrafts.controller;

import com.backend.handicrafts.dto.OrderRequest;
import com.backend.handicrafts.dto.OrderResponse;
import com.backend.handicrafts.dto.UpdateOrderStatusRequest;
import com.backend.handicrafts.entity.OrderStatus;
import com.backend.handicrafts.exception.BadRequestException;
import com.backend.handicrafts.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequest order) {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateOrderStatusRequest request) {
        OrderStatus status;
        try {
            status = OrderStatus.valueOf(request.getStatus().trim().toUpperCase());
        } catch (Exception ex) {
            throw new BadRequestException("Invalid order status. Allowed values: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED");
        }

        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
