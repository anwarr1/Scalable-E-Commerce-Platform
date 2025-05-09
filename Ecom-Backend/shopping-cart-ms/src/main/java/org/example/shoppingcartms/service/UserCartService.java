package org.example.shoppingcartms.service;

import lombok.AllArgsConstructor;
import org.example.shoppingcartms.config.feign.OrderClient;
import org.example.shoppingcartms.config.feign.ProductClient;
import org.example.shoppingcartms.config.feign.UserClient;
import org.example.shoppingcartms.model.dto.OrderItemDTO;
import org.example.shoppingcartms.model.entity.CartItem;
import org.example.shoppingcartms.model.entity.UserCart;
import org.example.shoppingcartms.repository.UserCartRepository;
import org.example.shoppingcartms.request.CreateOrderRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserCartService {
    private final UserCartRepository userCartRepository;
    private final ProductClient productClient;
    private final OrderClient orderClient;
    private final UserClient userClient;

    private static CreateOrderRequest getCreateOrderRequest(Long userId, List<CartItem> cartItemList, UserCart userCart) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItemList) {
            OrderItemDTO orderItem = new OrderItemDTO();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItems.add(orderItem);
        }
        CreateOrderRequest orderRequest = new CreateOrderRequest();
        orderRequest.setUserId(userId);
        orderRequest.setTotalPrice(userCart.getTotalPrice());
        orderRequest.setStatus("Pending");
        orderRequest.setOrderItemList(orderItems);
        return orderRequest;
    }

    @Transactional
    public void addToCart(Long userId, Long productId) {
        double total = 0.0;

        // Find userCart by userId, not id
        UserCart userCart = userCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserCart cart = new UserCart();
                    cart.setUserId(userId);
                    cart.setCartItemList(new ArrayList<>());
                    cart.setTotalPrice(0.0);
                    return cart;
                });

        List<CartItem> cartItemList = userCart.getCartItemList();
        boolean isProductInCart = false;

        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProductId().equals(productId)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                isProductInCart = true;
                break;
            }
        }

        if (!isProductInCart) {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(productId);
            cartItem.setQuantity(1);

            double productPrice = fetchProductPrice(productId); // You have to implement this
            cartItem.setPrice(productPrice);

            cartItemList.add(cartItem);
        }

        // Recalculate total price
        for (CartItem cartItem : cartItemList) {
            total += cartItem.getQuantity() * cartItem.getPrice();
        }
        userCart.setTotalPrice(total);

        userCart.setCartItemList(cartItemList);

        userCartRepository.save(userCart);
    }

    // Dummy method for now, you have to later call Product microservice
    private double fetchProductPrice(Long productId) {
        return productClient.getProductPrice(productId);
    }

    @Transactional

    public String removeFromCart(Long userId, Long productId) {

        UserCart userCart = userCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));
        List<CartItem> cartItemList = userCart.getCartItemList();
        CartItem cartItem = cartItemList.stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
        } else {
            cartItemList.remove(cartItem);
        }

        userCart.setCartItemList(cartItemList);
        userCartRepository.save(userCart);
        return "Removed successfully!";
    }

    public UserCart viewCart(Long userId) {
        return userCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));

    }

    public CreateOrderRequest checkout() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long userId = userClient.getUserId(email);

        UserCart userCart = userCartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User cart not found"));

        if (userCart.getCartItemList() == null || userCart.getCartItemList().isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot proceed to checkout.");
        }

        List<OrderItemDTO> orderItems = userCart.getCartItemList().stream()
                .map(cartItem -> new OrderItemDTO(
                        cartItem.getProductId(),
                        cartItem.getQuantity(),
                        cartItem.getPrice()
                )).toList();

        CreateOrderRequest orderRequest = CreateOrderRequest.builder()
                .userId(userId)
                .totalPrice(userCart.getTotalPrice())
                .status("Pending")
                .orderItemList(orderItems)
                .build();

        try {
            orderClient.placeOrder(orderRequest);

            // Clear cart after successful order
            userCart.getCartItemList().clear();
            userCart.setTotalPrice(0.0);
            userCartRepository.save(userCart);

            return orderRequest;
        } catch (Exception e) {
            // Consider logging here
            throw new RuntimeException("Error during checkout: " + e.getMessage(), e);
        }
    }
}

