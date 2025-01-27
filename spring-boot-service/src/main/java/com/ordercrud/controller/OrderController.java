package com.ordercrud.controller;

import com.ordercrud.dto.request.OrderRequestDTO;
import com.ordercrud.dto.response.OrderResponseDTO;
import com.ordercrud.service.OrderService;
import com.ordercrud.util.enums.Status;

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
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable int id) {
        try {
            OrderResponseDTO orderResDTO = orderService.findById(id);
            if (orderResDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(orderResDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error on getting order by id: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) String customerName) {

        try {
            Page<OrderResponseDTO> ordersPage = orderService.findAllPaged(page, size, status, customerName);

            return ResponseEntity.ok(ordersPage);
        } catch (Exception e) {
            throw new RuntimeException("Error on getting order paged: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequestDTO orderReq) {
        try {
            orderService.create(orderReq);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            throw new RuntimeException("Error on creating order: " + e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateOrder(@RequestBody OrderRequestDTO orderReq, @PathVariable int id) {
        try {
            orderService.update(id, orderReq);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new RuntimeException("Error on updating order: " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            orderService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new RuntimeException("Error on deleting order: " + e.getMessage());
        }
    }
}
