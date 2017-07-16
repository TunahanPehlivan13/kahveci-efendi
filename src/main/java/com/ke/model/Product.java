package com.ke.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic
    private String imgUrl;

    @Basic(optional = false)
    private BigDecimal price;

    public static class Builder {

        private String name;
        private BigDecimal price;
        private String imgUrl;

        public Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder imgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setImgUrl(imgUrl);
            return product;
        }
    }
}
