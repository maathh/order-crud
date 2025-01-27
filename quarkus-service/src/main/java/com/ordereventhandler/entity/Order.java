package com.ordereventhandler.entity;

import lombok.*;

import com.ordereventhandler.util.enums.Status;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends PanacheEntity {
    private String customerName;
    private String product;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Order() {
    }

    @Builder
    public Order(long id, String customerName, String product, int quantity, Status status) {
        this.id = id;
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }

}
