package com.ke.component.discount;

import com.ke.model.Order;
import com.ke.model.OrderItem;
import com.ke.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TotalProductCountOverDiscount extends DiscountApplicable {

    @Autowired
    private OrderItemService orderItemService;

    @Override
    BigDecimal calcDiscount(Order order) {
        boolean shouldApplyDiscount = orderItemService.findTotalProductCount(order.getOrderItems()) >= 3;
        if (!shouldApplyDiscount) {
            return BigDecimal.ZERO;
        }
        OrderItem orderItem = orderItemService.findLowestPriceWithToppings(order.getOrderItems());
        return orderItem.totalPrice();
    }

    @Override
    String discountProviderName() {
        return this.getClass().getSimpleName();
    }
}
