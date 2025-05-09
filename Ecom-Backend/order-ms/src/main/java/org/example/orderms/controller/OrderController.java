package org.example.orderms.controller;

import lombok.AllArgsConstructor;
import org.example.orderms.model.entity.Order;
import org.example.orderms.request.CreateOrderRequest;
import org.example.orderms.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor

@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(@RequestBody CreateOrderRequest order) {
        return orderService.placeOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }
}
