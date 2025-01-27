package com.ordereventhandler.dto.order;

public class OrderRequestDeleteDTO {

    public long id;

    public OrderRequestDeleteDTO() {
    }

    public OrderRequestDeleteDTO(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderRequestDeleteDTO{" +
                "id=" + id +
                '}';
    }
}
