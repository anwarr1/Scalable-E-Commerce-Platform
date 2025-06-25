package org.example.shoppingcartms.config.feign;

import org.example.shoppingcartms.request.CreateOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service") // product service URL

public interface OrderClient {


    @PostMapping("/orders/place")
    CreateOrderRequest placeOrder(@RequestBody  CreateOrderRequest order);
}
