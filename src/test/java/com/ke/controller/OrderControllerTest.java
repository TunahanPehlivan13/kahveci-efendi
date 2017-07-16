package com.ke.controller;

import com.ke.Application;
import com.ke.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class OrderControllerTest extends BaseControllerTest {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldReturnOkWhenGettingOrder() throws Exception {
        Product product = productRepository.findAll().get(0);
        Buyer buyer = buyerRepository.findAll().get(0);

        Cart cart = new Cart.Builder(product, 1, buyer)
                .build();

        cartRepository.save(cart);

        mockMvc.perform(get("/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice", notNullValue()))
                .andExpect(jsonPath("$.finalPrice", notNullValue()))
                .andExpect(jsonPath("$.orderItems", notNullValue()));

        cartRepository.deleteAll();
    }

    @Test
    public void shouldReturnNoContentWhenGettingOrderIfCartIsEmpty() throws Exception {
        mockMvc.perform(get("/order"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnOkWhenCreatingOrder() throws Exception {
        Product product = productRepository.findAll().get(0);
        Buyer buyer = buyerRepository.findAll().get(0);

        Cart cart = new Cart.Builder(product, 1, buyer)
                .build();

        cartRepository.save(cart);

        mockMvc.perform(post("/order"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPrice", is(4D)))
                .andExpect(jsonPath("$.finalPrice", is(4D)))
                .andExpect(jsonPath("$.orderItems", notNullValue()));

        orderRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    public void shouldReturnOkWhenQueryingOrders() throws Exception {
        List<Product> products = productRepository.findAll();

        OrderItem orderItem1 = new OrderItem.Builder(1, BigDecimal.valueOf(12), BigDecimal.valueOf(12)).product(products.get(0)).build();
        OrderItem orderItem2 = new OrderItem.Builder(1, BigDecimal.valueOf(2), BigDecimal.valueOf(5)).product(products.get(1)).build();
        List<OrderItem> orderItemsForOrder1 = Arrays.asList(orderItem1, orderItem2);

        OrderItem orderItem3 = new OrderItem.Builder(1, BigDecimal.valueOf(12), BigDecimal.valueOf(12)).product(products.get(3)).build();
        OrderItem orderItem4 = new OrderItem.Builder(1, BigDecimal.valueOf(2), BigDecimal.valueOf(5)).product(products.get(4)).build();
        List<OrderItem> orderItemsForOrder2 = Arrays.asList(orderItem3, orderItem4);

        OrderItem orderItem5 = new OrderItem.Builder(1, BigDecimal.valueOf(12), BigDecimal.valueOf(12)).product(products.get(2)).build();
        List<OrderItem> orderItemsForOrder3 = Arrays.asList(orderItem5);

        OrderItem orderItem6 = new OrderItem.Builder(1, BigDecimal.valueOf(12), BigDecimal.valueOf(12)).product(products.get(3)).build();
        List<OrderItem> orderItemsForOrder4 = Arrays.asList(orderItem6);

        Order order1 = new Order.Builder(BigDecimal.valueOf(1))
                .discountProvider("TotalCostOverDiscount")
                .orderItems(orderItemsForOrder1)
                .finalPrice(BigDecimal.valueOf(1))
                .build();
        Order order2 = new Order.Builder(BigDecimal.valueOf(2))
                .discountProvider("TotalProductCountOverDiscount")
                .orderItems(orderItemsForOrder2)
                .finalPrice(BigDecimal.valueOf(2))
                .build();
        Order order3 = new Order.Builder(BigDecimal.valueOf(3))
                .discountProvider("TotalCostOverDiscount")
                .orderItems(orderItemsForOrder3)
                .finalPrice(BigDecimal.valueOf(3))
                .build();
        Order order4 = new Order.Builder(BigDecimal.valueOf(4))
                .discountProvider("TotalCostOverDiscount")
                .orderItems(orderItemsForOrder4)
                .finalPrice(BigDecimal.valueOf(4))
                .build();

        orderItem1.setOrder(order1);
        orderItem2.setOrder(order1);
        orderItem3.setOrder(order2);
        orderItem4.setOrder(order2);
        orderItem5.setOrder(order3);
        orderItem6.setOrder(order4);

        List<Order> orders = Arrays.asList(order1, order2, order3, order4);

        orderRepository.save(orders);

        mockMvc.perform(get("/order/query")
                .param("productId", products.get(0).getId().toString())
                .param("discountType", "TotalProductCountOverDiscount"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"totalPrice\":1.00")))
                .andExpect(content().string(containsString("\"totalPrice\":2.00")));

        orderRepository.deleteAll();
    }
}