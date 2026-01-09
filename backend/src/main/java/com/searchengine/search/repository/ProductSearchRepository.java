package com.searchengine.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.searchengine.search.model.ProductDocument;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {
}
