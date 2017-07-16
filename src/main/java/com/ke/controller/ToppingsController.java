package com.ke.controller;


import com.ke.repository.ToppingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toppings")
@Slf4j
public class ToppingsController {

    @Autowired
    private ToppingsRepository toppingsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findToppings() {
        log.info("/toppings#GET is requested");
        return ResponseEntity.ok(toppingsRepository.findAll());
    }
}
