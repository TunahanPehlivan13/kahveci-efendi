package com.ke.component.discount;

import com.ke.model.Order;
import com.ke.model.OrderItem;
import com.ke.service.OrderItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TotalProductCountOverDiscountTest {

    @InjectMocks
    private TotalProductCountOverDiscount totalProductCountOverDiscount;

    @Mock
    private OrderItemService orderItemService;

    @Test
    public void shouldApplyLowestOrderItemPriceWhenHasTotalProductCountIsGreaterOrEqualThan3() {
        OrderItem orderItem1 = new OrderItem.Builder(1, BigDecimal.valueOf(3), BigDecimal.valueOf(2))
                .build();
        OrderItem orderItem2 = new OrderItem.Builder(2, BigDecimal.TEN, BigDecimal.TEN)
                .build();

        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);

        Order order = new Order.Builder(BigDecimal.valueOf(20))
                .orderItems(orderItems)
                .build();

        when(orderItemService.findTotalProductCount(order.getOrderItems())).thenReturn(3);
        when(orderItemService.findLowestPriceWithToppings(order.getOrderItems())).thenReturn(orderItem2);

        totalProductCountOverDiscount.apply(order);

        assertThat(order.getDiscountDetail().getTotalDiscount().compareTo(BigDecimal.valueOf(20)), equalTo(0));
        assertEquals(order.getDiscountDetail().getDiscountProvider(), "TotalProductCountOverDiscount");
    }

    @Test
    public void shouldDoNotApplyDiscountWhenHasTotalProductCountIsLessThan3() {
        OrderItem orderItem1 = new OrderItem.Builder(1, BigDecimal.valueOf(3), BigDecimal.valueOf(2))
                .build();
        OrderItem orderItem2 = new OrderItem.Builder(1, BigDecimal.TEN, BigDecimal.TEN)
                .build();

        List<OrderItem> orderItems = Arrays.asList(orderItem1, orderItem2);

        Order order = new Order.Builder(BigDecimal.valueOf(20))
                .orderItems(orderItems)
                .build();

        when(orderItemService.findTotalProductCount(order.getOrderItems())).thenReturn(2);

        totalProductCountOverDiscount.apply(order);

        assertThat(order.getDiscountDetail().getTotalDiscount().compareTo(BigDecimal.valueOf(0)), equalTo(0));
        assertNull(order.getDiscountDetail().getDiscountProvider());
    }
}