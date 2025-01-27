package com.ordercrud.dto.order;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ordercrud.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toOrder(OrderRequestDTO orderDTO) {

        return Order.builder()
                .customerName(orderDTO.getCustomerName())
                .product(orderDTO.getProduct())
                .quantity(orderDTO.getQuantity())
                .status(orderDTO.getStatus())
                .build();

    }

    public OrderResponseDTO toOrderDTO(Order order) {
        return new OrderResponseDTO(order);
    }

    public Page<OrderResponseDTO> toOrderDTO(Page<Order> orderList) {
        return orderList.map(OrderResponseDTO::new);
    }

    public List<OrderResponseDTO> toOrderDTO(List<Order> orderList) {
        return orderList.stream().map(OrderResponseDTO::new).collect(Collectors.toList());
    }

    public void updateOrderData(Order order, OrderRequestDTO orderDTO) {

        order.setCustomerName(orderDTO.getCustomerName());
        order.setProduct(orderDTO.getProduct());
        order.setQuantity(orderDTO.getQuantity());
        order.setStatus(orderDTO.getStatus());

    }

}