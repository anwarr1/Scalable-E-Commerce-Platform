package org.example.shoppingcartms.controller;

import lombok.AllArgsConstructor;
import org.example.shoppingcartms.model.entity.UserCart;
import org.example.shoppingcartms.request.CreateOrderRequest;
import org.example.shoppingcartms.request.UserCartRequest;
import org.example.shoppingcartms.service.UserCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("cart")
public class UserCartController {
    private final UserCartService userCartService;

    @PostMapping("/add")
    public UserCart addToCart(@RequestBody UserCartRequest userCartRequest) {
        return userCartService.addToCart(userCartRequest.getUserId(), userCartRequest.getProductId());

    }

    @PostMapping("/addMultiple")
    public UserCart addToCartAtOnce(@RequestBody UserCartRequest userCartRequest) {
        return userCartService.addToCartAtOnce(userCartRequest.getUserId(), userCartRequest.getProductId(),
                userCartRequest.getQuantity());

    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody UserCartRequest userCartRequest) {
        try {
            return ResponseEntity.ok(userCartService.removeFromCart(userCartRequest.getUserId(),
                    userCartRequest.getProductId()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewCart(@PathVariable Long id) {
        try {
            UserCart userCart = userCartService.viewCart(id);
            return ResponseEntity.ok(userCart);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User cart not found with ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout() {

        try {
            CreateOrderRequest createOrderRequest = userCartService.checkout();
            return ResponseEntity.ok(createOrderRequest);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
