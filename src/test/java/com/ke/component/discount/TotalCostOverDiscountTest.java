package com.ke.component.discount;

import com.ke.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TotalCostOverDiscountTest {

    @InjectMocks
    private TotalCostOverDiscount totalCostOverDiscount;

    @Test
    public void shouldApply25PercentDiscountWhenTotalPriceIsGreaterThan12() {
        Order order = new Order.Builder(BigDecimal.valueOf(20))
                .build();

        totalCostOverDiscount.apply(order);

        assertThat(order.getDiscountDetail().getTotalDiscount().compareTo(BigDecimal.valueOf(5)), equalTo(0));
        assertEquals(order.getDiscountDetail().getDiscountProvider(), "TotalCostOverDiscount");
    }

    @Test
    public void shouldDoNotApplyDiscountWhenTotalPriceIsLessThan12() {
        Order order = new Order.Builder(BigDecimal.valueOf(10))
                .build();

        totalCostOverDiscount.apply(order);

        assertThat(order.getDiscountDetail().getTotalDiscount().compareTo(BigDecimal.ZERO), equalTo(0));
        assertNull(order.getDiscountDetail().getDiscountProvider());
    }

    @Test
    public void shouldDoNotApplyDiscountWhenHasGreaterDiscount() {
        Order order = new Order.Builder(BigDecimal.valueOf(20))
                .discountProvider("TotalProductCountOverDiscount")
                .totalDiscount(BigDecimal.valueOf(6))
                .build();

        totalCostOverDiscount.apply(order);

        assertThat(order.getDiscountDetail().getTotalDiscount().compareTo(BigDecimal.valueOf(6)), equalTo(0));
        assertEquals(order.getDiscountDetail().getDiscountProvider(), "TotalProductCountOverDiscount");

    }
}
