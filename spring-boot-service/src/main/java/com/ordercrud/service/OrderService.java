package com.ordercrud.service;

import com.ordercrud.dto.order.OrderRequestDTO;
import com.ordercrud.dto.order.OrderResponseDTO;
import com.ordercrud.util.enums.Status;

import java.util.List;

import org.springframework.data.domain.Page;

public interface OrderService {

    OrderResponseDTO findById(long id);

    Page<OrderResponseDTO> findAllPaged(int page, int size, Status status, String customerName);

    List<OrderResponseDTO> findAll();

    void create(OrderRequestDTO orderDTO);

    void update(long id, OrderRequestDTO orderDTO);

    void delete(long id);
}
