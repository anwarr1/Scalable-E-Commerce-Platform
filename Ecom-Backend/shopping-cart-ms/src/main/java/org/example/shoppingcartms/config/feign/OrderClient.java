package org.example.shoppingcartms.config.feign;

import org.example.shoppingcartms.request.CreateOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", url = "http://localhost:8096") // product service URL

public interface OrderClient {


    @PostMapping("/api/order/place")
    CreateOrderRequest placeOrder(@RequestBody  CreateOrderRequest order);
}
