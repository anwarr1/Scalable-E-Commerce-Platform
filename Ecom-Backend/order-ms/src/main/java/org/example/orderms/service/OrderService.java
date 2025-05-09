package org.example.orderms.service;

import lombok.AllArgsConstructor;
import org.example.orderms.model.entity.Order;
import org.example.orderms.model.entity.OrderItem;
import org.example.orderms.model.mapper.OrderMapper;
import org.example.orderms.repository.OrderRepository;
import org.example.orderms.request.CreateOrderRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order placeOrder(CreateOrderRequest orderRequest) {
        System.out.println("Order request: " + orderRequest);
        Order order = OrderMapper.mapOrder(orderRequest);

        return orderRepository.save(order);
    }

    public Order getOrder(Long id) {

        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
