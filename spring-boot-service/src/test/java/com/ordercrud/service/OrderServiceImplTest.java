package com.ordercrud.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.ordercrud.adapter.messaging.MessagingProducer;
import com.ordercrud.dto.order.OrderMapper;
import com.ordercrud.dto.order.OrderRequestDTO;
import com.ordercrud.dto.order.OrderRequestDeleteDTO;
import com.ordercrud.dto.order.OrderResponseDTO;
import com.ordercrud.entity.Order;
import com.ordercrud.repository.OrderRepository;
import com.ordercrud.util.enums.Status;
import com.ordercrud.util.enums.Topic;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MessagingProducer messagingProducer;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ReturnsOrderResponseDTO_WhenOrderExists() {
        long orderId = 1L;
        Order order = new Order(orderId, "John Doe", "Product A", 5, Status.PENDING);
        OrderResponseDTO responseDTO = new OrderResponseDTO(new Order(orderId, "John Doe", "Product A", 5, Status.PENDING));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toOrderDTO(order)).thenReturn(responseDTO);

        OrderResponseDTO result = orderService.findById(orderId);

        assertEquals(responseDTO, result);
        verify(orderRepository).findById(orderId);
        verify(orderMapper).toOrderDTO(order);
    }

    @Test
    void findById_ThrowsException_WhenOrderDoesNotExist() {
        long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.findById(orderId));
        assertEquals("Order wasn't fount on database", exception.getMessage());
        verify(orderRepository).findById(orderId);
    }

    @Test
    void findAllPaged_ReturnsPagedOrders() {
        int page = 0;
        int size = 10;
        Status status = Status.PENDING;
        String customerName = "John Doe";

        Order order = new Order(1L, customerName, "Product A", 5, status);
        Page<Order> orders = new PageImpl<>(List.of(order));
        OrderResponseDTO responseDTO = new OrderResponseDTO(new Order(1L, customerName, "Product A", 5, status));
        Page<OrderResponseDTO> responsePage = new PageImpl<>(List.of(responseDTO));

        when(orderRepository.findAll(status, customerName, PageRequest.of(page, size))).thenReturn(orders);
        when(orderMapper.toOrderDTO(orders)).thenReturn(responsePage);

        Page<OrderResponseDTO> result = orderService.findAllPaged(page, size, status, customerName);

        assertEquals(responsePage, result);
        verify(orderRepository).findAll(status, customerName, PageRequest.of(page, size));
        verify(orderMapper).toOrderDTO(orders);
    }

    @Test
    void findAll_ReturnsAllOrders() {
        Order order = new Order(1L, "John Doe", "Product A", 5, Status.PENDING);
        List<Order> orders = List.of(order);
        OrderResponseDTO responseDTO = new OrderResponseDTO(new Order(1L, "John Doe", "Product A", 5, Status.PENDING));

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.toOrderDTO(orders)).thenReturn(List.of(responseDTO));

        List<OrderResponseDTO> result = orderService.findAll();

        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
        verify(orderRepository).findAll();
        verify(orderMapper).toOrderDTO(orders);
    }

    @Test
    void create_SendsMessageToProducer() {
        OrderRequestDTO orderRequest = new OrderRequestDTO("John Doe", "Product A", 5, Status.PENDING);

        orderService.create(orderRequest);

        verify(messagingProducer).send(Topic.ORDER_CREATE, orderRequest);
    }

    @Test
    void update_SendsMessageToProducer() {
        long orderId = 1L;
        OrderRequestDTO orderRequest = new OrderRequestDTO("John Doe", "Product A", 5, Status.PENDING);

        orderService.update(orderId, orderRequest);

        verify(messagingProducer).send(Topic.ORDER_UPDATE, orderRequest);
    }

    @Test
    void delete_SendsMessageToProducer() {
        long orderId = 1L;

        orderService.delete(orderId);

        verify(messagingProducer).send(eq(Topic.ORDER_DELETE), any(OrderRequestDeleteDTO.class));
    }
}
