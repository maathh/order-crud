package com.ordercrud.entity;

import lombok.*;

import com.ordercrud.util.enums.Status;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

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
