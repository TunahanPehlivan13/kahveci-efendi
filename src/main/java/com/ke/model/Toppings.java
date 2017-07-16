package com.ke.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Toppings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private BigDecimal price;

    public static class Builder {

        private String name;
        private BigDecimal price;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Toppings build() {
            Toppings toppings = new Toppings();
            toppings.setName(name);
            toppings.setPrice(price);
            return toppings;
        }
    }
}
