package com.ordercrud.dto.order;

import com.ordercrud.entity.Order;
import com.ordercrud.util.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class OrderResponseDTO {
    private long id;

    private String customerName;
    private String product;
    private long quantity;

    @Enumerated(EnumType.STRING)
    private Status status;

    public OrderResponseDTO() {}

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.customerName = order.getCustomerName();
        this.product = order.getProduct();
        this.quantity = order.getQuantity();
        this.status = order.getStatus();
    }
}
