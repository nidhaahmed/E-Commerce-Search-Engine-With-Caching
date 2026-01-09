package com.searchengine.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.searchengine.api.dto.SearchResponse;
import com.searchengine.search.model.ProductDocument;

@Service
public class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public SearchService(ElasticsearchOperations elasticsearchOperations,
            RedisTemplate<String, String> redisTemplate,
            ObjectMapper objectMapper) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public SearchResponse search(String query, int page, int size) {
        String cacheKey = "search:" + query + ":" + page + ":" + size;

        // 1. Check Cache
        try {
            String cachedJSON = redisTemplate.opsForValue().get(cacheKey);
            if (cachedJSON != null) {
                System.out.println("DEBUG: Cache HIT for " + query);
                return objectMapper.readValue(cachedJSON, SearchResponse.class);
            }
        } catch (Exception e) {
            // Log error but continue to search
            System.err.println("Redis cache read failed: " + e.getMessage());
        }

        System.out.println("DEBUG: Cache MISS. Querying ES for: " + query);

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .must(m -> m
                                        .multiMatch(mm -> mm
                                                .query(query)
                                                .fields("name^4", "description", "category^2", "brand^2")))))
                .withPageable(PageRequest.of(page, size))
                .build();

        SearchHits<ProductDocument> hits = elasticsearchOperations.search(nativeQuery, ProductDocument.class);
        System.out.println("DEBUG: ES returned " + hits.getTotalHits() + " hits for: " + query);

        List<ProductDocument> results = hits.stream()
                .map(SearchHit::getContent)
                .toList();

        SearchResponse response = new SearchResponse(query, page, size, hits.getTotalHits(), results);

        // 2. Write to Cache
        try {
            String json = objectMapper.writeValueAsString(response);
            redisTemplate.opsForValue().set(cacheKey, json, java.time.Duration.ofSeconds(60));
        } catch (Exception e) {
            System.err.println("Redis cache write failed: " + e.getMessage());
        }

        return response;
    }

    public void resetIndex() {
        System.out.println("DEBUG: Resetting 'products' index...");
        org.springframework.data.elasticsearch.core.IndexOperations indexOps = elasticsearchOperations
                .indexOps(ProductDocument.class);
        indexOps.delete();
        indexOps.create();
        indexOps.putMapping();
        System.out.println("DEBUG: 'products' index reset COMPLETE");
    }

    public long getTotalHits() {
        NativeQuery allQuery = NativeQuery.builder().withQuery(q -> q.matchAll(m -> m)).build();
        SearchHits<ProductDocument> hits = elasticsearchOperations.search(allQuery, ProductDocument.class);
        return hits.getTotalHits();
    }
}
