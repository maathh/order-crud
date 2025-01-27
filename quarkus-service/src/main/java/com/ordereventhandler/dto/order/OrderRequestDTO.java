package com.ordereventhandler.dto.order;

import com.ordereventhandler.util.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO {
    private long id;
    private String customerName;
    private String product;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Status status;
}
