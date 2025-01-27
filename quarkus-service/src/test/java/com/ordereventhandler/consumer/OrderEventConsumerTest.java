package com.ordereventhandler.consumer;

import com.ordereventhandler.dto.order.OrderRequestDTO;
import com.ordereventhandler.dto.order.OrderRequestDeleteDTO;
import com.ordereventhandler.dto.order.OrderRequestUpdateDTO;
import com.ordereventhandler.service.OrderService;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    static void begin(){
        kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
        kafkaContainer.start();
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
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
        doNothing().when(orderService).updateOrder(orderRequestUpdateDTO);

        orderEventConsumer.consumeOrderUpdate(orderRequestUpdateDTO);

        verify(orderService, times(1)).updateOrder(orderRequestUpdateDTO);
    }

    @Test
    void testConsumeOrderCreate() {
        doNothing().when(orderService).createOrder(orderRequestDTO);

        orderEventConsumer.consumeOrderCreate(orderRequestDTO);

        verify(orderService, times(1)).createOrder(orderRequestDTO);
    }

    @Test
    void testConsumeOrderDelete() {
        doNothing().when(orderService).deleteOrder(orderRequestDeleteDTO);

        orderEventConsumer.consumeOrderDelete(orderRequestDeleteDTO);

        verify(orderService, times(1)).deleteOrder(orderRequestDeleteDTO);
    }

    @Test
    void testConsumeOrderUpdateWithException() {
        doThrow(new RuntimeException("Error updating order")).when(orderService).updateOrder(orderRequestUpdateDTO);

        orderEventConsumer.consumeOrderUpdate(orderRequestUpdateDTO);

        verify(orderService, times(1)).updateOrder(orderRequestUpdateDTO);
    }

    @Test
    void testConsumeOrderCreateWithException() {
        doThrow(new RuntimeException("Error creating order")).when(orderService).createOrder(orderRequestDTO);

        orderEventConsumer.consumeOrderCreate(orderRequestDTO);

        verify(orderService, times(1)).createOrder(orderRequestDTO);
    }

    @Test
    void testConsumeOrderDeleteWithException() {
        doThrow(new RuntimeException("Error deleting order")).when(orderService).deleteOrder(orderRequestDeleteDTO);

        orderEventConsumer.consumeOrderDelete(orderRequestDeleteDTO);

        verify(orderService, times(1)).deleteOrder(orderRequestDeleteDTO);
    }
}
