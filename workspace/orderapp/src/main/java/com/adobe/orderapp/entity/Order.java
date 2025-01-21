package com.adobe.orderapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    private int oid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="order_date")
    private Date orderDate = new Date(); // system date

    // order is by a customer
    @ManyToOne
    @JoinColumn(name="customer_fk")
    private Customer customer;

    // order has many line items
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>();

    private double total;
}
