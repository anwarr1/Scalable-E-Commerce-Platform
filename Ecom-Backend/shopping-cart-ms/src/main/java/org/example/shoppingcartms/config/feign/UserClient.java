package org.example.shoppingcartms.config.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service") // product service URL
public interface UserClient {

    @GetMapping("/auth/getUserId")
    Long getUserId(@RequestParam("email") String email);
}

