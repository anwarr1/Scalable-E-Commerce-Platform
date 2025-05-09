package org.example.shoppingcartms.repository;

import org.example.shoppingcartms.model.entity.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCartRepository extends JpaRepository<UserCart, Long> {
    // Custom query methods can be defined here if needed
    Optional<UserCart> findByUserId(Long userId);
}
