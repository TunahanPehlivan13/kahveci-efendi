package com.ke.component.discount;

import com.ke.model.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TotalCostOverDiscount extends DiscountApplicable {

    @Override
    BigDecimal calcDiscount(Order order) {
        BigDecimal minCostForDiscount = BigDecimal.valueOf(12);
        if (order.getTotalPrice().compareTo(minCostForDiscount) < 0) {
            return BigDecimal.ZERO;
        }

        return order.getTotalPrice().multiply(BigDecimal.valueOf(0.25d));
    }

    @Override
    String discountProviderName() {
        return this.getClass().getSimpleName();
    }
}
