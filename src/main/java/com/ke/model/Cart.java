package com.ke.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    @ManyToOne(optional = false)
    private Product product;

    @ManyToMany
    @JoinTable(name = "cart_toppings",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "toppings_id"))
    private Set<Toppings> toppings;

    @Basic(optional = false)
    private Integer quantity;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Buyer buyer;

    public static class Builder {

        private Product product;
        private Set<Toppings> toppings;
        private Integer quantity;
        private Buyer buyer;

        public Builder(Product product, Integer quantity, Buyer buyer) {
            if (quantity == null || product == null || buyer == null) {
                throw new IllegalArgumentException("quantity or quantity or buyer can not be null!");
            }
            this.quantity = quantity;
            this.product = product;
            this.buyer = buyer;
        }

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder toppings(Set<Toppings> toppings) {
            this.toppings = toppings;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder buyer(Buyer buyer) {
            this.buyer = buyer;
            return this;
        }

        public Cart build() {
            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setToppings(toppings);
            cart.setBuyer(buyer);
            return cart;
        }
    }
}
