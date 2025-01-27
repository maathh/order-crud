package com.ordereventhandler.dto.order;

import com.ordereventhandler.util.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class OrderRequestUpdateDTO {

    public long id;

    public String customerName;
    public String product;
    public int quantity;

    @Enumerated(EnumType.STRING)
    public Status status;

    public OrderRequestUpdateDTO() {
    }

    public OrderRequestUpdateDTO(long id, String customerName, String product, int quantity, Status status) {
        this.id = id;
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderRequestUpdateDTO{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
