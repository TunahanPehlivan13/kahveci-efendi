package com.ke.controller;

import com.ke.Application;
import com.ke.model.Toppings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ToppingsControllerTest extends BaseControllerTest {

    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void shouldReturnOkWhenGettingToppings() throws Exception {
        Toppings toppings = new Toppings.Builder()
                .name("dummy")
                .price(BigDecimal.TEN)
                .build();

        toppingsRepository.save(toppings);

        mockMvc.perform(get("/toppings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].price", notNullValue()))
                .andExpect(jsonPath("$[0].name", notNullValue()));

        toppingsRepository.delete(toppings);
    }
}