package com.ordereventhandler.dto;

import com.ordereventhandler.dto.order.OrderRequestDTO;
import com.ordereventhandler.util.enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRequestDTOTest {

    @Test
    void testDefaultConstructor() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();

        assertNull(orderRequestDTO.getCustomerName());
        assertNull(orderRequestDTO.getProduct());
        assertEquals(0, orderRequestDTO.getQuantity());
        assertNull(orderRequestDTO.getStatus());
    }

    @Test
    void testParameterizedConstructor() {
        String customerName = "John Doe";
        String product = "Laptop";
        int quantity = 2;
        Status status = Status.PENDING;

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(customerName, product, quantity, status);

        assertEquals(customerName, orderRequestDTO.getCustomerName());
        assertEquals(product, orderRequestDTO.getProduct());
        assertEquals(quantity, orderRequestDTO.getQuantity());
        assertEquals(status, orderRequestDTO.getStatus());
    }

    @Test
    void testSetterAndGetter() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        String customerName = "Jane Doe";
        String product = "Phone";
        int quantity = 5;
        Status status = Status.SHIPPED;

        orderRequestDTO.setCustomerName(customerName);
        orderRequestDTO.setProduct(product);
        orderRequestDTO.setQuantity(quantity);
        orderRequestDTO.setStatus(status);

        assertEquals(customerName, orderRequestDTO.getCustomerName());
        assertEquals(product, orderRequestDTO.getProduct());
        assertEquals(quantity, orderRequestDTO.getQuantity());
        assertEquals(status, orderRequestDTO.getStatus());
    }

    @Test
    void testToString() {
        String customerName = "John Smith";
        String product = "Tablet";
        int quantity = 3;
        Status status = Status.PENDING;
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(customerName, product, quantity, status);

        String result = orderRequestDTO.toString();

        assertTrue(result.contains(customerName));
        assertTrue(result.contains(product));
        assertTrue(result.contains(String.valueOf(quantity)));
        assertTrue(result.contains(status.name()));
    }
}
