package com.ordereventhandler.service;

import com.ordereventhandler.dto.order.OrderMapper;
import com.ordereventhandler.dto.order.OrderRequestDTO;
import com.ordereventhandler.dto.order.OrderRequestDeleteDTO;
import com.ordereventhandler.dto.order.OrderRequestUpdateDTO;
import com.ordereventhandler.entity.Order;
import com.ordereventhandler.repository.OrderRepository;
import com.ordereventhandler.util.enums.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private OrderRequestDTO orderRequestDTO;
    private OrderRequestUpdateDTO orderRequestUpdateDTO;
    private OrderRequestDeleteDTO orderRequestDeleteDTO;
    private Order order;

    @BeforeEach
    void setUp() {
        orderRequestDTO = new OrderRequestDTO("John Doe", "Laptop", 1, Status.PENDING);
        orderRequestUpdateDTO = new OrderRequestUpdateDTO(1L, "John Doe", "Smartphone", 2, Status.PENDING);
        orderRequestDeleteDTO = new OrderRequestDeleteDTO(1L);

        order = new Order();
        order.setId(1L);
        order.setCustomerName("John Doe");
        order.setProduct("Laptop");
        order.setQuantity(1);
        order.setStatus(Status.PENDING);
    }

    @Test
    void createOrder_success() {
        when(orderMapper.toOrder(orderRequestDTO)).thenReturn(order);

        orderService.createOrder(orderRequestDTO);

        verify(orderMapper, times(1)).toOrder(orderRequestDTO);
        verify(orderRepository, times(1)).persist(order);
    }

    @Test
    void updateOrder_success() {
        when(orderRepository.findById(1L)).thenReturn(order);

        orderService.updateOrder(orderRequestUpdateDTO);

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).update(order);
        assertEquals("Smartphone", order.getProduct());
        assertEquals(2, order.getQuantity());
        assertEquals(Status.PENDING, order.getStatus());
    }

    @Test
    void updateOrder_orderNotFound() {
        when(orderRepository.findById(1L)).thenReturn(null);

        orderService.updateOrder(orderRequestUpdateDTO);

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).update(any(Order.class));
    }

    @Test
    void deleteOrder_success() {
        orderService.deleteOrder(orderRequestDeleteDTO);

        verify(orderRepository, times(1)).deleteById(1L);
    }
}
