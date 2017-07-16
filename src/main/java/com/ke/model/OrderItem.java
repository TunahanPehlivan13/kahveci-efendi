package com.ke.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.corba.se.internal.iiop.ORB;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    @ManyToMany
    @JoinTable(name = "orderitem_toppings",
            joinColumns = @JoinColumn(name = "orderitem_id"),
            inverseJoinColumns = @JoinColumn(name = "toppings_id"))
    private Set<Toppings> toppings;

    @Basic(optional = false)
    private Integer quantity;

    @Basic(optional = false)
    private BigDecimal priceOfProduct;

    @Basic(optional = false)
    private BigDecimal priceOfToppings;

    public BigDecimal totalPrice() {
        return priceOfProduct.add(priceOfToppings);
    }

    public BigDecimal totalPriceWithQuantity() {
        return priceOfProduct
                .add(priceOfToppings)
                .multiply(BigDecimal.valueOf(quantity));
    }

    public static class Builder {

        private Order order;
        private Product product;
        private Set<Toppings> toppings;
        private Integer quantity;
        private BigDecimal priceOfProduct;
        private BigDecimal priceOfToppings;

        public Builder(Integer quantity, BigDecimal priceOfProduct, BigDecimal priceOfToppings) {
            if (quantity == null || priceOfProduct == null || priceOfToppings == null) {
                throw new IllegalArgumentException("quantity or priceOfProduct or priceOfToppings can not be null!");
            }
            this.quantity = quantity;
            this.priceOfProduct = priceOfProduct;
            this.priceOfToppings = priceOfToppings;
        }

        public Builder order(Order order) {
            this.order = order;
            return this;
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

        public Builder priceOfProduct(BigDecimal priceOfProduct) {
            this.priceOfProduct = priceOfProduct;
            return this;
        }

        public Builder priceOfToppings(BigDecimal priceOfToppings) {
            this.priceOfToppings = priceOfToppings;
            return this;
        }

        public OrderItem build() {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPriceOfProduct(priceOfProduct);
            orderItem.setPriceOfToppings(priceOfToppings);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setToppings(toppings);
            return orderItem;
        }
    }
}