package com.ordercrud.dto.order;


import com.ordercrud.adapter.messaging.MessagingData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDeleteDTO implements MessagingData {
    private long id;

    public OrderRequestDeleteDTO(long id){
        this.id = id;
    }
}
