package com.backend.handicrafts.dto;

import com.backend.handicrafts.entity.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Long userId;
}
