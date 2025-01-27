package com.ordereventhandler.consumer;

import com.ordereventhandler.dto.order.OrderRequestDTO;
import com.ordereventhandler.dto.order.OrderRequestDeleteDTO;
import com.ordereventhandler.dto.order.OrderRequestUpdateDTO;
import com.ordereventhandler.service.OrderService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class OrderEventConsumer {

    private static final Logger LOG = Logger.getLogger(OrderEventConsumer.class);

    @Inject
    OrderService orderService;

    @Incoming("ORDER_UPDATE")
    public void consumeOrderUpdate(OrderRequestUpdateDTO orderRequestDTO) {
        LOG.infof("consumeOrderUpdate:begin - Payload: %s", orderRequestDTO);

        try {
            orderService.updateOrder(orderRequestDTO);
            LOG.infof("consumeOrderUpdate:success - %d", orderRequestDTO.getId());
        } catch (Exception e) {
            LOG.errorf("consumeOrderUpdate:error - %d. Error: %s",
                    orderRequestDTO.getId(), e.getMessage());
        }
    }

    @Incoming("ORDER_CREATE")
    public void consumeOrderCreate(OrderRequestDTO orderRequestDTO) {
        LOG.infof("consumeOrderCreate:begin - Payload: %s", orderRequestDTO);

        try {
            orderService.createOrder(orderRequestDTO);
            LOG.info("consumeOrderCreate:success");
        } catch (Exception e) {
            LOG.errorf("consumeOrderCreate:error - %s. Error: %s",
                    orderRequestDTO, e.getMessage());
        }
    }

    @Incoming("ORDER_DELETE")
    public void consumeOrderDelete(OrderRequestDeleteDTO orderRequestDeleteDTO) {
        LOG.infof("consumeOrderDelete:begin - Order ID: %d", orderRequestDeleteDTO.getId());

        try {
            orderService.deleteOrder(orderRequestDeleteDTO);
            LOG.infof("consumeOrderDelete:success - Order ID: %d deleted successfully", orderRequestDeleteDTO.getId());
        } catch (Exception e) {
            LOG.errorf("consumeOrderDelete:error - %d. Error: %s",
                    orderRequestDeleteDTO.getId(), e.getMessage());
        }
    }
}
