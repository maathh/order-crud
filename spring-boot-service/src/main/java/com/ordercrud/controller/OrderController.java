package com.ordercrud.controller;

import com.ordercrud.dto.order.OrderRequestDTO;
import com.ordercrud.dto.order.OrderResponseDTO;
import com.ordercrud.service.OrderService;
import com.ordercrud.util.enums.Status;

import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable long id) {
        Span span = GlobalTracer.get().buildSpan("getOrderById").start();
        try {
            OrderResponseDTO orderResDTO = orderService.findById(id);
            if (orderResDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(orderResDTO);
        } catch (Exception e) {
            span.setTag("error", true); 
            span.log("Error on getting order by id: " + e.getMessage());
            throw new RuntimeException("Error on getting order by id: " + e.getMessage());
        } finally {
            span.finish(); 
        }
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String customerName) {

        Span span = GlobalTracer.get().buildSpan("getOrders").start();
        try {
            Page<OrderResponseDTO> ordersPage = orderService.findAllPaged(page, size, status, customerName);
            return ResponseEntity.ok(ordersPage);
        } catch (Exception e) {
            span.setTag("getOrders:Catch", true);  
            span.log("getOrders:Catch - " + e.getMessage());
            throw new RuntimeException("getOrders:Catch - " + e.getMessage());
        } finally {
            span.finish();  
        }
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequestDTO orderReq) {
        Span span = GlobalTracer.get().buildSpan("createOrder").start();
        try {
            orderService.create(orderReq);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            span.setTag("error", true);  // Marca o span como erro em caso de exceção
            span.log("Error on creating order: " + e.getMessage());
            throw new RuntimeException("Error on creating order: " + e.getMessage());
        } finally {
            span.finish();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateOrder(@RequestBody OrderRequestDTO orderReq, @PathVariable long id) {
        Span span = GlobalTracer.get().buildSpan("updateOrder").start();
        try {
            orderService.update(id, orderReq);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            span.setTag("error", true);  
            span.log("Error on updating order: " + e.getMessage());
            throw new RuntimeException("Error on updating order: " + e.getMessage());
        } finally {
            span.finish(); 
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        Span span = GlobalTracer.get().buildSpan("deleteOrder").start();
        try {
            orderService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            span.setTag("error", true); 
            span.log("Error on deleting order: " + e.getMessage());
            throw new RuntimeException("Error on deleting order: " + e.getMessage());
        } finally {
            span.finish();  
        }
    }
}
