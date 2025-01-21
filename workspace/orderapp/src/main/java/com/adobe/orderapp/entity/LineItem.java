package com.adobe.orderapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="line_items")
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO INCREMENT
    @Column(name = "item_id")
    private int itemId;

    // line item refers to a Product

    @ManyToOne
    @JoinColumn(name="product_fk")
    private Product product;

    private int qty;

    private double amount; // price * qty + tax - discount - COUPON

}
