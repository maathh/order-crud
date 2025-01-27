package com.ordereventhandler.dto.order;

import com.ordereventhandler.util.enums.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class OrderRequestDTO {

    public String customerName;
    public String product;
    public int quantity;

    @Enumerated(EnumType.STRING)
    public Status status;

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(String customerName, String product, int quantity, Status status) {
        this.customerName = customerName;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderRequestDTO{" +
                "customerName='" + customerName + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
