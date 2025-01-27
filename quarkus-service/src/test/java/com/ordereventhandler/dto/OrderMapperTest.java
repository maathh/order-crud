package com.ordereventhandler.dto;

import com.ordereventhandler.dto.order.OrderMapper;
import com.ordereventhandler.dto.order.OrderRequestDTO;
import com.ordereventhandler.entity.Order;
import com.ordereventhandler.util.enums.Status;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class OrderMapperTest {

    @Inject
    OrderMapper orderMapper;

    @Test
    void testToOrder() {
        // Arrange
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setCustomerName("Customer Test");
        orderRequestDTO.setProduct("Test Product");
        orderRequestDTO.setQuantity(10);
        orderRequestDTO.setStatus(Status.PENDING);

        // Act
        Order order = orderMapper.toOrder(orderRequestDTO);

        // Assert
        assertNotNull(order);
        assertEquals("Customer Test", order.getCustomerName());
        assertEquals("Test Product", order.getProduct());
        assertEquals(10, order.getQuantity());
        assertEquals(Status.PENDING, order.getStatus());
    }
}
