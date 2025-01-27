package com.ordereventhandler.repository;

import com.ordereventhandler.entity.Order;
import com.ordereventhandler.util.enums.Status;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class OrderRepositoryTest {

    @Inject
    OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setCustomerName("Customer Test");
        order.setProduct("Test Product");
        order.setQuantity(10);
        order.setStatus(Status.PENDING);
    }

    @Test
    @Transactional
    void testSaveOrder() {
        orderRepository.save(order);
        assertNotNull(order.getId());
        assertEquals("Customer Test", order.getCustomerName());
        assertEquals("Test Product", order.getProduct());
        assertEquals(10, order.getQuantity());
        assertEquals(Status.PENDING, order.getStatus());
    }

    @Test
    @Transactional
    void testFindById() {
        orderRepository.save(order);
        Order foundOrder = orderRepository.findById(order.getId());
        assertNotNull(foundOrder);
        assertEquals(order.getId(), foundOrder.getId());
        assertEquals(order.getCustomerName(), foundOrder.getCustomerName());
        assertEquals(order.getProduct(), foundOrder.getProduct());
        assertEquals(order.getQuantity(), foundOrder.getQuantity());
        assertEquals(order.getStatus(), foundOrder.getStatus());
    }

    @Test
    @Transactional
    void testUpdateOrder() {
        orderRepository.save(order);
        order.setCustomerName("Updated Customer Name");
        order.setProduct("Updated Product");
        order.setQuantity(20);
        order.setStatus(Status.PENDING);
        orderRepository.update(order);
        Order updatedOrder = orderRepository.findById(order.getId());
        assertNotNull(updatedOrder);
        assertEquals("Updated Customer Name", updatedOrder.getCustomerName());
        assertEquals("Updated Product", updatedOrder.getProduct());
        assertEquals(20, updatedOrder.getQuantity());
        assertEquals(Status.PENDING, updatedOrder.getStatus());
    }

    @Test
    @Transactional
    void testDeleteOrder() {
        orderRepository.save(order);
        long orderId = order.getId();
        orderRepository.delete(orderId);
        Order deletedOrder = orderRepository.findById(orderId);
        assertNull(deletedOrder);
    }
}
