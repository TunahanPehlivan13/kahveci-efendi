package com.ke.service;

import com.ke.model.Buyer;
import com.ke.repository.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    public Buyer getCurrentBuyer() {
        return buyerRepository.findAll().get(0);
    }
}
