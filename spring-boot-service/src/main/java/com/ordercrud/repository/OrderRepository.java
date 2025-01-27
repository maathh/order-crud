package com.ordercrud.repository;

import com.ordercrud.entity.Order;
import com.ordercrud.util.enums.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Integer> {

        @Query("SELECT o FROM Order o WHERE " +
                "(:status IS NULL OR o.status = :status) " +
                "AND (:customerName IS NULL OR LOWER(CAST(o.customerName AS string)) LIKE LOWER(CONCAT('%', :customerName, '%')))")
        Page<Order> findAll(
                @Param("status") Status status,
                @Param("customerName") String customerName,
                Pageable pageable);
    }