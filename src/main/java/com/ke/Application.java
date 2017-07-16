package com.ke;

import com.ke.model.Buyer;
import com.ke.model.Product;
import com.ke.model.Toppings;
import com.ke.repository.BuyerRepository;
import com.ke.repository.ProductRepository;
import com.ke.repository.ToppingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ToppingsRepository toppingsRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Product kahve = new Product();
        kahve.setName("Filtre Kahve");
        kahve.setPrice(BigDecimal.valueOf(4));

        Product latte = new Product();
        latte.setName("Latte");
        latte.setPrice(BigDecimal.valueOf(5));

        Product mocha = new Product();
        mocha.setName("Mocha");
        mocha.setPrice(BigDecimal.valueOf(6));

        Product cay = new Product();
        cay.setName("Çay");
        cay.setPrice(BigDecimal.valueOf(3));

        Product turkkahvesi = new Product();
        turkkahvesi.setName("Türk Kahvesi");
        turkkahvesi.setPrice(BigDecimal.valueOf(5));

        productRepository.save(Arrays.asList(kahve, latte, mocha, cay, turkkahvesi));

        Toppings sut = new Toppings();
        sut.setName("Süt");
        sut.setPrice(BigDecimal.valueOf(2));

        Toppings findiksurubu = new Toppings();
        findiksurubu.setName("Fındık Şurubu");
        findiksurubu.setPrice(BigDecimal.valueOf(3));

        Toppings cikolatasosu = new Toppings();
        cikolatasosu.setName("Çikolata Sosu");
        cikolatasosu.setPrice(BigDecimal.valueOf(5));

        Toppings limon = new Toppings();
        limon.setName("Limon");
        limon.setPrice(BigDecimal.valueOf(2));

        toppingsRepository.save(Arrays.asList(sut, findiksurubu, cikolatasosu, limon));

        Buyer buyer = new Buyer();
        buyer.setEmail("dummy@email.com");

        buyerRepository.save(buyer);
    }
}
