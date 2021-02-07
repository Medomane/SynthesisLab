package com.sid.kafka.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private Long id ;
    private String name;
    private double price;
    private int quantityAvailable;
    private Long supplierId ;
}
