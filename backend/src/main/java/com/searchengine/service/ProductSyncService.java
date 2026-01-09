package com.searchengine.service;

import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import com.searchengine.search.model.ProductDocument;
import com.searchengine.search.repository.ProductSearchRepository;

@Service
public class ProductSyncService {

    private final ProductSearchRepository productSearchRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public ProductSyncService(ProductSearchRepository productSearchRepository,
            RedisTemplate<String, String> redisTemplate) {
        this.productSearchRepository = productSearchRepository;
        this.redisTemplate = redisTemplate;
    }

    @Async
    public void syncProducts(List<ProductDocument> documents) {
        if (documents == null || documents.isEmpty()) {
            System.out.println("DEBUG: No documents to sync");
            return;
        }

        try {
            System.out.println("DEBUG: Syncing " + documents.size() + " documents to Elasticsearch...");
            // 1. Save to Elasticsearch
            productSearchRepository.saveAll(documents);
            System.out.println("DEBUG: Indexed " + documents.size() + " products SUCCESSFULLY");

            // 2. Invalidate Search Cache
            Set<String> keys = redisTemplate.keys("search:*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                System.out.println("DEBUG: Invalidated " + keys.size() + " cache keys");
            }

        } catch (Exception e) {
            System.err.println("DEBUG ERROR: Sync failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
