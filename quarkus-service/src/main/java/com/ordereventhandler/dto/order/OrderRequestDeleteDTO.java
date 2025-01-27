package com.ordereventhandler.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDeleteDTO {
    private long id;

    public OrderRequestDeleteDTO(int id){
        this.id = id;
    }
}
