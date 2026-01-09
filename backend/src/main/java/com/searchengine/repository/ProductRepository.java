package com.searchengine.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.searchengine.model.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
