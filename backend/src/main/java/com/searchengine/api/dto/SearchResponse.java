package com.searchengine.api.dto;

import java.util.List;
import com.searchengine.search.model.ProductDocument;

public class SearchResponse {
    private String query;
    private int page;
    private int size;
    private long totalHits;
    private List<ProductDocument> results;

    public SearchResponse(String query, int page, int size, long totalHits, List<ProductDocument> results) {
        this.query = query;
        this.page = page;
        this.size = size;
        this.totalHits = totalHits;
        this.results = results;
    }

    // Getters
    public String getQuery() {
        return query;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalHits() {
        return totalHits;
    }

    public List<ProductDocument> getResults() {
        return results;
    }
}
