package org.example.orderms.request;

import lombok.Builder;
import lombok.Data;
import org.example.orderms.model.dto.OrderItemDTO;

import java.util.List;

@Data
@Builder
public class CreateOrderRequest {

    private Long userId;
    private Double totalPrice;
    private String status;

    private List<OrderItemDTO> orderItemList;
}
