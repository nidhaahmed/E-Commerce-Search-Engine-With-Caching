package com.searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.searchengine.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
