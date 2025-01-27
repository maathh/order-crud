package com.ordercrud.dto.request;


import com.ordercrud.adapter.messaging.MessagingData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDeleteDTO implements MessagingData {
    private int id;

    public OrderRequestDeleteDTO(int id){
        this.id = id;
    }
}
