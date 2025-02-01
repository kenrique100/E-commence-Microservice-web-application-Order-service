package com.akentech.microservice.product.repository;

import com.akentech.microservice.product.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing product entities in MongoDB.
 */
@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}