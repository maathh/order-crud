package com.ordercrud.dto.order;


import com.ordercrud.adapter.messaging.MessagingData;
import com.ordercrud.util.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO implements MessagingData {
    private long id;
    private String customerName;
    private String product;
    private int quantity;

    public OrderRequestDTO() {
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    public OrderRequestDTO(String customerName, String product, int quantity, Status status) {
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }
}
