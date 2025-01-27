package com.ordereventhandler.consumer;

import com.ordereventhandler.dto.order.OrderRequestDTO;
import com.ordereventhandler.dto.order.OrderRequestDeleteDTO;
import com.ordereventhandler.dto.order.OrderRequestUpdateDTO;
import com.ordereventhandler.service.OrderService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;

import static org.mockito.Mockito.*;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class OrderEventConsumerTest {

    @InjectMocks
    OrderEventConsumer orderEventConsumer;

    @Mock
    OrderService orderService;

    private OrderRequestDTO orderRequestDTO;
    private OrderRequestUpdateDTO orderRequestUpdateDTO;
    private OrderRequestDeleteDTO orderRequestDeleteDTO;

    private static KafkaContainer kafkaContainer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
        kafkaContainer.start();

        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setCustomerName("Order 1");

        orderRequestUpdateDTO = new OrderRequestUpdateDTO();
        orderRequestUpdateDTO.setId(1L);
        orderRequestUpdateDTO.setCustomerName("Updated Order");

        orderRequestDeleteDTO = new OrderRequestDeleteDTO();
        orderRequestDeleteDTO.setId(1L);
    }

    @AfterAll
    public static void tearDown() {
        if (kafkaContainer != null) {
            kafkaContainer.stop();
        }
    }

    @Test
    void testConsumeOrderUpdate() {
        // Arrange
        doNothing().when(orderService).updateOrder(orderRequestUpdateDTO);

        // Act
        orderEventConsumer.consumeOrderUpdate(orderRequestUpdateDTO);

        // Assert
        verify(orderService, times(1)).updateOrder(orderRequestUpdateDTO);
    }

    @Test
    void testConsumeOrderCreate() {
        // Arrange
        doNothing().when(orderService).createOrder(orderRequestDTO);

        // Act
        orderEventConsumer.consumeOrderCreate(orderRequestDTO);

        // Assert
        verify(orderService, times(1)).createOrder(orderRequestDTO);
    }

    @Test
    void testConsumeOrderDelete() {
        // Arrange
        doNothing().when(orderService).deleteOrder(orderRequestDeleteDTO);

        // Act
        orderEventConsumer.consumeOrderDelete(orderRequestDeleteDTO);

        // Assert
        verify(orderService, times(1)).deleteOrder(orderRequestDeleteDTO);
    }

    @Test
    void testConsumeOrderUpdateWithException() {
        // Arrange
        doThrow(new RuntimeException("Error updating order")).when(orderService).updateOrder(orderRequestUpdateDTO);

        // Act
        orderEventConsumer.consumeOrderUpdate(orderRequestUpdateDTO);

        // Assert
        verify(orderService, times(1)).updateOrder(orderRequestUpdateDTO);
    }

    @Test
    void testConsumeOrderCreateWithException() {
        // Arrange
        doThrow(new RuntimeException("Error creating order")).when(orderService).createOrder(orderRequestDTO);

        // Act
        orderEventConsumer.consumeOrderCreate(orderRequestDTO);

        // Assert
        verify(orderService, times(1)).createOrder(orderRequestDTO);
    }

    @Test
    void testConsumeOrderDeleteWithException() {
        // Arrange
        doThrow(new RuntimeException("Error deleting order")).when(orderService).deleteOrder(orderRequestDeleteDTO);

        // Act
        orderEventConsumer.consumeOrderDelete(orderRequestDeleteDTO);

        // Assert
        verify(orderService, times(1)).deleteOrder(orderRequestDeleteDTO);
    }
}
