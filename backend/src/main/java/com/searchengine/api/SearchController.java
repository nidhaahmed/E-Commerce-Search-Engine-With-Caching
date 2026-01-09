package com.searchengine.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.searchengine.api.dto.SearchResponse;
import com.searchengine.service.SearchService;

@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                searchService.search(q, page, size));
    }

    @PostMapping("/search/reset")
    public ResponseEntity<java.util.Map<String, String>> resetIndex() {
        searchService.resetIndex();
        return ResponseEntity.ok(java.util.Map.of("message", "Index reset initiated"));
    }

    @GetMapping("/search/status")
    public ResponseEntity<java.util.Map<String, Object>> getStatus() {
        return ResponseEntity.ok(java.util.Map.of(
                "totalHitsInES", searchService.getTotalHits(),
                "indexSettings", "Check ES directly for settings"));
    }
}
