package com.ke.component.discount;

import com.ke.model.Order;

import java.math.BigDecimal;

public abstract class DiscountApplicable {

    abstract BigDecimal calcDiscount(Order order);

    abstract String discountProviderName();

    public void apply(Order order) {
        BigDecimal discount = calcDiscount(order);
        boolean isGreaterDiscount = discount.compareTo(order.getDiscountDetail().getTotalDiscount()) > 0;
        if (isGreaterDiscount) {
            order.getDiscountDetail().setDiscountProvider(discountProviderName());
            order.getDiscountDetail().setTotalDiscount(discount);
        }
    }
}
