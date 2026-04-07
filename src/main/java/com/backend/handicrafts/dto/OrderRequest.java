package com.backend.handicrafts.dto;

import com.backend.handicrafts.entity.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequest {
    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal totalAmount;

    @NotNull
    private OrderStatus status;
}
