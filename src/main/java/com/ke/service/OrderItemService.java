package com.ke.service;

import com.ke.model.Cart;
import com.ke.model.OrderItem;
import com.ke.model.Toppings;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderItemService {

    public OrderItem createOrderItem(Cart cart) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cart.getProduct());
        orderItem.setQuantity(cart.getQuantity());
        orderItem.setToppings(new HashSet<>(cart.getToppings()));
        orderItem.setPriceOfProduct(cart.getProduct().getPrice());

        BigDecimal priceOfToppings = cart.getToppings()
                .stream()
                .map(Toppings::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderItem.setPriceOfToppings(priceOfToppings);
        return orderItem;
    }

    public OrderItem findLowestPriceWithToppings(List<OrderItem> orderItems) {
        return orderItems.stream()
                .sorted((o1, o2) -> o1.totalPrice().compareTo(o2.totalPrice()))
                .findFirst()
                .orElse(null);
    }

    public int findTotalProductCount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .mapToInt(o -> o.getQuantity())
                .sum();
    }
}
