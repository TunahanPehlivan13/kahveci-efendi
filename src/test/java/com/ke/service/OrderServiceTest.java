package com.ke.service;

import com.ke.component.discount.DiscountApplicable;
import com.ke.component.discount.TotalCostOverDiscount;
import com.ke.exception.NoItemsInCartException;
import com.ke.model.*;
import com.ke.repository.CartRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private BuyerService buyerService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private TotalCostOverDiscount totalCostOverDiscount;

    private List<? extends DiscountApplicable> discountApplicables;

    @Before
    public void setUp() throws Exception {
        discountApplicables = Arrays.asList(totalCostOverDiscount);
        orderService.setDiscountApplicables(discountApplicables);
    }

    @Test
    public void shouldCreateOrder() {
        Buyer buyer = new Buyer();

        Product product1 = new Product.Builder().build();
        Product product2 = new Product.Builder().build();

        Cart cart1 = new Cart.Builder(product1, 2, buyer)
                .build();
        Cart cart2 = new Cart.Builder(product2, 1, buyer)
                .build();

        OrderItem orderItem1 = new OrderItem.Builder(2, BigDecimal.valueOf(12), BigDecimal.valueOf(4)).build();
        OrderItem orderItem2 = new OrderItem.Builder(1, BigDecimal.valueOf(6), BigDecimal.valueOf(8)).build();

        List<Cart> cartItems = Arrays.asList(cart1, cart2);

        when(buyerService.getCurrentBuyer()).thenReturn(buyer);
        when(cartRepository.findByBuyer(buyer)).thenReturn(cartItems);
        when(orderItemService.createOrderItem(cart1)).thenReturn(orderItem1);
        when(orderItemService.createOrderItem(cart2)).thenReturn(orderItem2);

        Order order = orderService.createOrder();

        assertThat(order.getBuyer(), equalTo(buyer));
        assertThat(order.getTotalPrice().compareTo(BigDecimal.valueOf(46)), is(0));
        assertThat(order.getFinalPrice().compareTo(BigDecimal.valueOf(46)), is(0));
    }

    @Test(expected = NoItemsInCartException.class)
    public void shouldThrowNoItemsInCartExceptionWhenCartIsEmpty() {
        Buyer buyer = new Buyer();

        when(buyerService.getCurrentBuyer()).thenReturn(buyer);
        when(cartRepository.findByBuyer(buyer)).thenReturn(Lists.emptyList());

        orderService.createOrder();
    }
}