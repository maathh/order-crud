package com.ordercrud.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ordercrud.adapter.messaging.MessagingData;
import com.ordercrud.adapter.messaging.MessagingProducer;
import com.ordercrud.dto.mapper.OrderMapper;
import com.ordercrud.dto.request.OrderRequestDTO;
import com.ordercrud.dto.request.OrderRequestDeleteDTO;
import com.ordercrud.dto.response.OrderResponseDTO;
import com.ordercrud.entity.Order;
import com.ordercrud.enums.Status;
import com.ordercrud.enums.Topic;
import com.ordercrud.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MessagingProducer messagingProducer;
    private final OrderMapper orderMapper;

    public OrderResponseDTO findById(int id) {
        return orderMapper.toOrderDTO(returnOrder(id));
    }

    public Page<OrderResponseDTO> findAllPaged(int page, int size, Status status, String customerName) {
        return orderMapper.toOrderDTO(orderRepository.findAll(status, customerName, PageRequest.of(page, size)));
    }

    public List<OrderResponseDTO> findAll() {
        return orderMapper.toOrderDTO(orderRepository.findAll());
    }

    public void create(OrderRequestDTO order) {
        send(Topic.ORDER_CREATE, order);
    }

    public void update(int id, OrderRequestDTO order) {
        send(Topic.ORDER_UPDATE, order);
    }

    public void delete(int id) {
        OrderRequestDeleteDTO orderRequest = new OrderRequestDeleteDTO(id);
        orderRequest.setId(id);

        send(Topic.ORDER_DELETE, orderRequest);
    }

    private void send(Topic topic, MessagingData orderRequest) {
        messagingProducer.send(topic, orderRequest);
    }

    private Order returnOrder(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order wasn't fount on database"));
    }
}
