package com.ordercrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ordercrud.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
