package com.backend.handicrafts.service;

import com.backend.handicrafts.dto.OrderRequest;
import com.backend.handicrafts.dto.OrderResponse;
import com.backend.handicrafts.entity.OrderStatus;
import com.backend.handicrafts.entity.Order;
import com.backend.handicrafts.exception.ResourceNotFoundException;
import com.backend.handicrafts.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse createOrder(OrderRequest request) {
        Order order = modelMapper.map(request, Order.class);
        order.setId(null);
        Order savedOrder = orderRepository.save(order);
        log.info("DATA INSERTED SUCCESSFULLY: Order{{id={}, totalAmount={}}}",
                savedOrder.getId(), savedOrder.getTotalAmount());
        return toResponse(savedOrder);
    }

    public OrderResponse updateOrder(Long id, OrderRequest payload) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        existing.setTotalAmount(payload.getTotalAmount());
        existing.setStatus(payload.getStatus());

        return toResponse(orderRepository.save(existing));
    }

    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        existing.setStatus(status);
        return toResponse(orderRepository.save(existing));
    }

    public void deleteOrder(Long id) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(existing);
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse response = modelMapper.map(order, OrderResponse.class);
        if (order.getUser() != null) {
            response.setUserId(order.getUser().getId());
        }
        return response;
    }
}
