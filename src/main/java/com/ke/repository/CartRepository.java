package com.ke.repository;

import com.ke.model.Buyer;
import com.ke.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByBuyer(Buyer buyer);

    void deleteByBuyer(Buyer buyer);
}
