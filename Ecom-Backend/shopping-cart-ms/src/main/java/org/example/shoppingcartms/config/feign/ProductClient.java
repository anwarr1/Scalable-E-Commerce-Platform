package org.example.shoppingcartms.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-catalog-service", url = "http://localhost:8098") // product service URL
public interface ProductClient {

    @GetMapping("/api/products/{id}/price")
    Double getProductPrice(@PathVariable("id") Long productId);
}

