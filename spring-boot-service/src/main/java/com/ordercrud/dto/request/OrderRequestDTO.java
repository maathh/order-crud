package com.ordercrud.dto.request;


import com.ordercrud.adapter.messaging.MessagingData;
import com.ordercrud.util.enums.Status;
import com.ordercrud.util.enums.Topic;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO implements MessagingData {
    private int id;
    private String customerName;
    private String product;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Status status;
}
