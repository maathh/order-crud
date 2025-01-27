package com.ordereventhandler.service;

import com.ordereventhandler.dto.order.OrderMapper;
import com.ordereventhandler.dto.order.OrderRequestDTO;
import com.ordereventhandler.dto.order.OrderRequestDeleteDTO;
import com.ordereventhandler.dto.order.OrderRequestUpdateDTO;
import com.ordereventhandler.entity.Order;
import com.ordereventhandler.repository.OrderRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.jboss.logging.Logger;

@ApplicationScoped
public class OrderService {

    private static final Logger LOG = Logger.getLogger(OrderService.class);

    @Inject
    OrderRepository orderRepository;

    @Inject
    OrderMapper orderMapper;
    @Transactional
    public void createOrder(OrderRequestDTO orderRequestDTO) {
        LOG.infof("createOrder - begin: %s", orderRequestDTO);
        Order newOrder = orderMapper.toOrder(orderRequestDTO);
        LOG.infof("createOrder - toOrder: %s", newOrder);
        orderRepository.persist(newOrder);
        LOG.infof("createOrder - finish: %d", newOrder.getId());
    }

    @Transactional
    public void updateOrder(OrderRequestUpdateDTO orderRequestDTO) {
        Order order = orderRepository.findById(orderRequestDTO.getId());
        if (order != null) {
            order.setCustomerName(orderRequestDTO.getCustomerName());
            order.setProduct(orderRequestDTO.getProduct());
            order.setQuantity(orderRequestDTO.getQuantity());
            order.setStatus(orderRequestDTO.getStatus());
            orderRepository.update(order);
        }
    }

    @Transactional
    public void deleteOrder(OrderRequestDeleteDTO orderRequestDeleteDTO) {
        orderRepository.deleteById(orderRequestDeleteDTO.getId());
    }
}
