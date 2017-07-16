package com.ke.repository;

import com.ke.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select distinct o " +
            "from orders o " +
            "left join o.orderItems oi " +
            "where o.discountDetail.discountProvider = :discountType " +
            "or oi.product.id = :productId")
    List<Order> query(@Param(value = "productId") Long productId,
                      @Param(value = "discountType") String discountType);
}
