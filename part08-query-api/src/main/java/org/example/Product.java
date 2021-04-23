package org.example;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author pilseong.ko
 */
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private int orderAmount;

    private String name;

    private int price;

    private int stockAmount;
}
