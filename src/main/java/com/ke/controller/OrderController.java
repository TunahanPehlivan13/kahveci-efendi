package com.ke.controller;

import com.ke.model.Order;
import com.ke.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getOrder() {
        log.info("/order#GET is requested");
        return ResponseEntity.ok(orderService.createOrder());
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ResponseEntity<?> query(@RequestParam(required = false) Long productId, @RequestParam(required = false) String discountType) {
        log.info("/order/query#GET is requested");
        return ResponseEntity.ok(orderService.query(productId, discountType));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save() {
        log.info("/order#POST is requested");
        Order order = orderService.createOrder();
        orderService.completeOrder(order);
        return new ResponseEntity(order, HttpStatus.CREATED);
    }
}
