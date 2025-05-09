package org.example.orderms.model.mapper;

import org.example.orderms.model.dto.OrderItemDTO;
import org.example.orderms.model.entity.Order;
import org.example.orderms.model.entity.OrderItem;
import org.example.orderms.request.CreateOrderRequest;

import java.util.List;

public class OrderMapper {
    public static Order mapOrder(CreateOrderRequest orderRequest) {
        List<OrderItemDTO> orderItemDTOList = orderRequest.getOrderItemList();
        return Order.builder()
                .userId(orderRequest.getUserId())
                .totalPrice(orderRequest.getTotalPrice())
                .status(orderRequest.getStatus())
                .orderItemList(orderItemDTOList != null ? orderItemDTOList.stream()
                        .map(OrderMapper::mapOrderItem).toList() : null)
                .build();
    }

    public static OrderItem mapOrderItem(OrderItemDTO orderItemDTO) {
        return OrderItem.builder().
                productId(orderItemDTO.getProductId())
                .quantity(orderItemDTO.getQuantity())
                .price(orderItemDTO.getPrice())
                .build();
    }
}
