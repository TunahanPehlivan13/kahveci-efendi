package com.ke.controller;

import com.ke.model.Cart;
import com.ke.repository.CartRepository;
import com.ke.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BuyerService buyerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@Valid @RequestBody Cart cart) {
        log.info("/cart#POST is requested");
        cart.setBuyer(buyerService.getCurrentBuyer());
        cartRepository.save(cart);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
}
