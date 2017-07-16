package com.ke.service;

import com.ke.model.*;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemServiceTest {

    @InjectMocks
    private OrderItemService orderItemService;

    @Test
    public void shouldFindTotalProductCount() {
        List<OrderItem> orderItems = Arrays.asList(
                new OrderItem.Builder(1, BigDecimal.ONE, BigDecimal.ONE)
                        .build(),
                new OrderItem.Builder(5, BigDecimal.ONE, BigDecimal.ONE)
                        .build(),
                new OrderItem.Builder(10, BigDecimal.ONE, BigDecimal.ONE)
                        .build()
        );

        int totalProductCount = orderItemService.findTotalProductCount(orderItems);

        assertThat(totalProductCount, equalTo(16));
    }

    @Test
    public void shouldFindZeroProductCountWhenHasEmptyList() {
        int totalProductCount = orderItemService.findTotalProductCount(Lists.emptyList());

        assertThat(totalProductCount, equalTo(0));
    }

    @Test
    public void shouldCreateOrderItem() {
        Product product = new Product.Builder()
                .price(BigDecimal.TEN)
                .build();
        Buyer buyer = new Buyer();

        Set<Toppings> toppings = Sets.newLinkedHashSet(
                new Toppings.Builder()
                        .price(BigDecimal.TEN)
                        .build(),
                new Toppings.Builder()
                        .price(BigDecimal.ONE)
                        .build()
        );
        Cart cart = new Cart.Builder(product, 2, buyer)
                .toppings(toppings)
                .build();

        OrderItem orderItem = orderItemService.createOrderItem(cart);

        assertThat(orderItem.getPriceOfProduct(), equalTo(BigDecimal.TEN));
        assertThat(orderItem.getPriceOfToppings(), equalTo(BigDecimal.valueOf(11)));
        assertThat(orderItem.getProduct(), equalTo(product));
        assertThat(orderItem.getQuantity(), equalTo(2));
        assertThat(orderItem.getToppings(), equalTo(toppings));
    }

    @Test
    public void shouldFindLowestPriceWithToppings() {
        OrderItem orderItem1 = new OrderItem.Builder(1, BigDecimal.valueOf(2), BigDecimal.valueOf(3))
                .build();
        OrderItem orderItem2 = new OrderItem.Builder(3, BigDecimal.valueOf(12), BigDecimal.valueOf(6))
                .build();
        OrderItem orderItem3 = new OrderItem.Builder(2, BigDecimal.valueOf(3), BigDecimal.valueOf(5))
                .build();
        List<OrderItem> orderItems = Arrays.asList(orderItem3, orderItem1, orderItem2);

        OrderItem lowestPriceOrderItem = orderItemService.findLowestPriceWithToppings(orderItems);

        assertEquals(orderItem1, lowestPriceOrderItem);
    }
}