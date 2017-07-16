package com.ke.controller;

import com.ke.Application;
import com.ke.model.Buyer;
import com.ke.model.Cart;
import com.ke.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CartControllerTest extends BaseControllerTest {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldReturnCreatedWhenAddingNewCart() throws Exception {
        Product product = productRepository.findAll().get(0);
        Cart cart = new Cart.Builder(product, 1, new Buyer()).build();

        mockMvc.perform(post("/cart")
                .content(this.json(cart))
                .contentType(contentType))
                .andExpect(status().isCreated());

        cartRepository.deleteAll();
    }
}