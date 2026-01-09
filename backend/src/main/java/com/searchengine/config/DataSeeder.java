package com.searchengine.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.searchengine.api.dto.CreateProductRequest;
import com.searchengine.service.ProductService;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductService productService;

    public DataSeeder(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productService.count() == 0) {
            System.out.println("🌱 Seeding initial data...");

            List<CreateProductRequest> products = List.of(
                    new CreateProductRequest("Apple iPhone 14", "Latest Apple smartphone with A15 Bionic chip",
                            "Electronics", "Apple", 999.99, "https://example.com/iphone14.jpg", 100),
                    new CreateProductRequest("Samsung Galaxy S23", "Flagship Samsung phone with high-res camera",
                            "Electronics", "Samsung", 899.99, "https://example.com/s23.jpg", 100),
                    new CreateProductRequest("Sony WH-1000XM5", "Noise cancelling wireless headphones", "Electronics",
                            "Sony", 349.99, "https://example.com/sony-headphones.jpg", 50),
                    new CreateProductRequest("MacBook Pro 16", "M2 Pro chip for professionals", "Electronics", "Apple",
                            2499.00, "https://example.com/macbook.jpg", 30),
                    new CreateProductRequest("Dell XPS 13", "Ultra-thin laptop with infinity display", "Electronics",
                            "Dell", 1200.00, "https://example.com/dell-xps.jpg", 40),
                    new CreateProductRequest("Logitech MX Master 3S", "Ergonomic wireless mouse", "Electronics",
                            "Logitech", 99.99, "https://example.com/mouse.jpg", 200),
                    new CreateProductRequest("Kindle Paperwhite", "Waterproof e-reader with adjustable warm light",
                            "Electronics", "Amazon", 139.99, "https://example.com/kindle.jpg", 150));

            productService.bulkCreate(products);
            System.out.println("✅ Data seeding completed!");
        } else {
            System.out.println("⚡ Data already exists, skipping seeding.");
        }
    }
}
