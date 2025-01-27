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

    @Enumerated(EnumType.STRING)
    private Status status;
}
