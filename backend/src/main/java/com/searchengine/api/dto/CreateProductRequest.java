package com.searchengine.api.dto;

import java.math.BigDecimal;

public class CreateProductRequest {

    public CreateProductRequest(String name, String description, String category, String brand, double price,
            String imageUrl, int stock) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = BigDecimal.valueOf(price);
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public String name;
    public String description;
    public String category;
    public String brand;
    public BigDecimal price;
    public String imageUrl;
    public Integer stock;
}
