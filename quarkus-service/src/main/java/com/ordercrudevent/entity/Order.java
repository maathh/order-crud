package com.ordercrudevent.entity;

import com.ordercrud.enums.Status;

import lombok.*;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    private String customerName ;
    private String product ;
    private int quantity  ;

    @Enumerated(EnumType.STRING) 
    private Status status;

    @Builder
    public Order(String customerName, String product, int quantity, Status status) {
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }

}
