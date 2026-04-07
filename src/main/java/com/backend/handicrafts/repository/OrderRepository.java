package com.backend.handicrafts.repository;

import com.backend.handicrafts.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
