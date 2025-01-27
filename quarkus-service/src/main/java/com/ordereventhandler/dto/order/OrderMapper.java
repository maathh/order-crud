package com.ordereventhandler.dto.order;

import com.ordereventhandler.entity.Order;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderMapper {

    public Order toOrder(OrderRequestDTO orderDTO) {

        return Order.builder()
                .id(orderDTO.getId())
                .customerName(orderDTO.getCustomerName())
                .product(orderDTO.getProduct())
                .quantity(orderDTO.getQuantity())
                .status(orderDTO.getStatus())
                .build();

    }

}