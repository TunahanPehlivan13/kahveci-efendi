package com.ke.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ke.model.embedded.DiscountDetail;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Basic(optional = false)
    private BigDecimal totalPrice;

    @Embedded
    private DiscountDetail discountDetail = new DiscountDetail();

    @Basic(optional = false)
    private BigDecimal finalPrice;

    @JsonIgnore
    @ManyToOne
    private Buyer buyer;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                '}';
    }

    public static class Builder {

        private BigDecimal totalPrice;
        private BigDecimal totalDiscount = BigDecimal.ZERO;
        private String discountProvider;
        private BigDecimal finalPrice;
        private Buyer buyer;
        private List<OrderItem> orderItems = new ArrayList<>();

        public Builder(BigDecimal totalPrice) {
            if (totalPrice == null) {
                throw new IllegalArgumentException("totalPrice can not be null!");
            }
            this.totalPrice = totalPrice;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder totalDiscount(BigDecimal totalDiscount) {
            this.totalDiscount = totalDiscount;
            return this;
        }

        public Builder discountProvider(String discountProvider) {
            this.discountProvider = discountProvider;
            return this;
        }

        public Builder finalPrice(BigDecimal finalPrice) {
            this.finalPrice = finalPrice;
            return this;
        }

        public Builder buyer(Buyer buyer) {
            this.buyer = buyer;
            return this;
        }

        public Builder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setBuyer(buyer);
            order.getDiscountDetail().setDiscountProvider(discountProvider);
            order.getDiscountDetail().setTotalDiscount(totalDiscount);
            order.setFinalPrice(finalPrice);
            order.setOrderItems(orderItems);
            order.setTotalPrice(totalPrice);
            return order;
        }
    }
}
