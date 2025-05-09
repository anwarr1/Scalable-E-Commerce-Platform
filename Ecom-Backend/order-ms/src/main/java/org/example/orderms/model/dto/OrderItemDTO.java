package org.example.orderms.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDTO {
    private Long productId;
    private int quantity;
    private Double price;


}
