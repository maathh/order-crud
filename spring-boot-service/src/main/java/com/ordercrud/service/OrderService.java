package com.ordercrud.service;

import com.ordercrud.dto.request.OrderRequestDTO;
import com.ordercrud.dto.response.OrderResponseDTO;
import com.ordercrud.enums.Status;

import java.util.List;

import org.springframework.data.domain.Page;

public interface OrderService {

    OrderResponseDTO findById(int id);

    Page<OrderResponseDTO> findAllPaged(int page, int size, Status status, String customerName);

    List<OrderResponseDTO> findAll();

    void create(OrderRequestDTO orderDTO);

    void update(int id, OrderRequestDTO orderDTO);

    void delete(int id);
}
