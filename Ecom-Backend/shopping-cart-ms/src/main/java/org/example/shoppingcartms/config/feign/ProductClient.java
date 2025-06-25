package org.example.shoppingcartms.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-catalog-service") // product service URL
public interface ProductClient {

    @GetMapping("/products/{id}/price")
    Double getProductPrice(@PathVariable("id") Long productId);
}

