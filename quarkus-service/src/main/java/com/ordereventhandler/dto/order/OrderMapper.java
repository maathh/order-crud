package com.ordereventhandler.dto.order;

import com.ordereventhandler.entity.Order;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderMapper {

    public Order toOrder(OrderRequestDTO orderDTO) {
        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setProduct(orderDTO.getProduct());
        order.setQuantity(orderDTO.getQuantity());
        order.setStatus(orderDTO.getStatus());
        return order;
    }
}