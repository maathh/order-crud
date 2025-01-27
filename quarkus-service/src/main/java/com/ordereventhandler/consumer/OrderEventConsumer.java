package com.ordereventhandler.consumer;

import com.ordereventhandler.dto.order.OrderMapper;
import com.ordereventhandler.dto.order.OrderRequestDTO;
import com.ordereventhandler.dto.order.OrderRequestDeleteDTO;
import com.ordereventhandler.entity.Order;
import com.ordereventhandler.repository.OrderRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.eclipse.microprofile.reactive.messaging.Incoming;

public class OrderEventConsumer {

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderMapper orderMapper;

    @Incoming("order-update")
    @Transactional
    public void consumeOrderUpdate(OrderRequestDTO orderRequestDTO) {
        Order order = orderRepository.findByIdOptional(orderRequestDTO.getId())
                .orElseGet(() -> this.orderMapper.toOrder(orderRequestDTO));

        order.setCustomerName(orderRequestDTO.getCustomerName());
        order.setProduct(orderRequestDTO.getProduct());
        order.setQuantity(orderRequestDTO.getQuantity());
        order.setStatus(orderRequestDTO.getStatus());

        orderRepository.update(order);
    }

    @Incoming("order-create")
    @Transactional
    public void consumeOrderCreate(OrderRequestDTO orderRequestDTO) {
        Order newOrder = this.orderMapper.toOrder(orderRequestDTO);

        orderRepository.persist(newOrder);
    }

    @Incoming("order-delete")
    @Transactional
    public void consumeOrderDelete(OrderRequestDeleteDTO orderRequestDeleteDTO) {
        orderRepository.deleteById(orderRequestDeleteDTO.getId());
    }
}
