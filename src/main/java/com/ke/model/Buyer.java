package com.ke.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private String email;
}
