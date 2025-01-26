package com.ordercrudevent.model;

public class Order {

    public String id;
    public int price;

    public Order() { }

    public Order(String id, int price) {
        this.id = id;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", price=" + price +
                '}';
    }
}