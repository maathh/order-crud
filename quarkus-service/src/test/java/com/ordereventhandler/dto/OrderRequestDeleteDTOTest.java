package com.ordereventhandler.dto;

import org.junit.jupiter.api.Test;

import com.ordereventhandler.dto.order.OrderRequestDeleteDTO;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRequestDeleteDTOTest {

    @Test
    void testDefaultConstructor() {
        OrderRequestDeleteDTO orderRequestDeleteDTO = new OrderRequestDeleteDTO();

        assertEquals(0, orderRequestDeleteDTO.getId());
    }

    @Test
    void testParameterizedConstructor() {
        long id = 123L;

        OrderRequestDeleteDTO orderRequestDeleteDTO = new OrderRequestDeleteDTO(id);

        assertEquals(id, orderRequestDeleteDTO.getId());
    }

    @Test
    void testSetterAndGetter() {
        OrderRequestDeleteDTO orderRequestDeleteDTO = new OrderRequestDeleteDTO();
        long newId = 456L;

        orderRequestDeleteDTO.setId(newId);

        assertEquals(newId, orderRequestDeleteDTO.getId());
    }

    @Test
    void testToString() {
        long id = 123L;
        OrderRequestDeleteDTO orderRequestDeleteDTO = new OrderRequestDeleteDTO(id);

        String result = orderRequestDeleteDTO.toString();

        assertTrue(result.contains("id=" + id));
    }
}
