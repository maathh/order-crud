package com.ordereventhandler.dto;

import com.ordereventhandler.dto.order.OrderRequestUpdateDTO;
import com.ordereventhandler.util.enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRequestUpdateDTOTest {

    @Test
    void testDefaultConstructor() {
        OrderRequestUpdateDTO orderRequestUpdateDTO = new OrderRequestUpdateDTO();

        assertEquals(0, orderRequestUpdateDTO.getId());
        assertNull(orderRequestUpdateDTO.getCustomerName());
        assertNull(orderRequestUpdateDTO.getProduct());
        assertEquals(0, orderRequestUpdateDTO.getQuantity());
        assertNull(orderRequestUpdateDTO.getStatus());
    }

    @Test
    void testParameterizedConstructor() {
        long id = 1L;
        String customerName = "Alice";
        String product = "Smartphone";
        int quantity = 3;
        Status status = Status.PENDING;

        OrderRequestUpdateDTO orderRequestUpdateDTO = new OrderRequestUpdateDTO(id, customerName, product, quantity, status);

        assertEquals(id, orderRequestUpdateDTO.getId());
        assertEquals(customerName, orderRequestUpdateDTO.getCustomerName());
        assertEquals(product, orderRequestUpdateDTO.getProduct());
        assertEquals(quantity, orderRequestUpdateDTO.getQuantity());
        assertEquals(status, orderRequestUpdateDTO.getStatus());
    }

    @Test
    void testSetterAndGetter() {
        OrderRequestUpdateDTO orderRequestUpdateDTO = new OrderRequestUpdateDTO();
        long id = 2L;
        String customerName = "Bob";
        String product = "Laptop";
        int quantity = 5;
        Status status = Status.SHIPPED;

        orderRequestUpdateDTO.setId(id);
        orderRequestUpdateDTO.setCustomerName(customerName);
        orderRequestUpdateDTO.setProduct(product);
        orderRequestUpdateDTO.setQuantity(quantity);
        orderRequestUpdateDTO.setStatus(status);

        assertEquals(id, orderRequestUpdateDTO.getId());
        assertEquals(customerName, orderRequestUpdateDTO.getCustomerName());
        assertEquals(product, orderRequestUpdateDTO.getProduct());
        assertEquals(quantity, orderRequestUpdateDTO.getQuantity());
        assertEquals(status, orderRequestUpdateDTO.getStatus());
    }

    @Test
    void testToString() {
        long id = 3L;
        String customerName = "Charlie";
        String product = "Tablet";
        int quantity = 7;
        Status status = Status.PENDING;
        OrderRequestUpdateDTO orderRequestUpdateDTO = new OrderRequestUpdateDTO(id, customerName, product, quantity, status);

        String result = orderRequestUpdateDTO.toString();

        assertTrue(result.contains(String.valueOf(id)));
        assertTrue(result.contains(customerName));
        assertTrue(result.contains(product));
        assertTrue(result.contains(String.valueOf(quantity)));
        assertTrue(result.contains(status.name()));
    }
}
