package org.example.shoppingcartms.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCartRequest {
    Long userId;
    Long productId;
    int quantity;
}
