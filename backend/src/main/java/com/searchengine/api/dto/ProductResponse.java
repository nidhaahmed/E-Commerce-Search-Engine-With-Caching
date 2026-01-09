package com.searchengine.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponse {

    public UUID id;
    public String name;
    public String category;
    public String brand;
    public BigDecimal price;
    public String imageUrl;
}
