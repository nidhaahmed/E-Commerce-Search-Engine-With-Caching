package com.searchengine.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.searchengine.api.dto.CreateProductRequest;
import com.searchengine.model.Product;
import com.searchengine.repository.ProductRepository;
import com.searchengine.search.model.ProductDocument;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductSyncService productSyncService;

    public ProductService(ProductRepository productRepository,
            ProductSyncService productSyncService) {
        this.productRepository = productRepository;
        this.productSyncService = productSyncService;
    }

    @Transactional
    public int bulkCreate(List<CreateProductRequest> requests) {

        List<Product> products = requests.stream().map(req -> {
            Product p = new Product();
            // DO NOT SET ID
            p.setName(req.name);
            p.setDescription(req.description);
            p.setCategory(req.category);
            p.setBrand(req.brand);
            p.setPrice(req.price);
            p.setImageUrl(req.imageUrl);
            p.setStock(req.stock);
            return p;
        }).toList();

        List<Product> savedProducts = productRepository.saveAll(products);

        // Map to documents
        List<ProductDocument> documents = savedProducts.stream()
                .map(p -> new ProductDocument(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getCategory(),
                        p.getBrand(),
                        p.getPrice(),
                        p.getStock()))
                .toList();

        // Async Sync
        productSyncService.syncProducts(documents);

        return savedProducts.size();
    }

    public Page<Product> getAll(int page, int size) {
        return productRepository.findAll(
                PageRequest.of(page, size));
    }

    public long count() {
        return productRepository.count();
    }
}
