package org.example.shoppingcartms.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shoppingcartms.model.dto.OrderItemDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    private Long userId;
    private double totalPrice;
    private String status;

    private List<OrderItemDTO> orderItemList;
}
