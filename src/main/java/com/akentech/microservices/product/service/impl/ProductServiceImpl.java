package com.akentech.microservices.product.service.impl;

import com.akentech.microservices.product.dto.ProductRequest;
import com.akentech.microservices.product.dto.ProductResponse;
import com.akentech.microservices.product.exception.ProductNotFoundException;
import com.akentech.microservices.product.kafka.ProductProducer;
import com.akentech.microservices.product.model.Product;
import com.akentech.microservices.product.repository.ProductRepository;
import com.akentech.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service implementation for handling product-related operations.
 * Uses Spring WebFlux for reactive programming.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductProducer productProducer;

    @Override
    public Mono<ProductResponse> createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .categoryId(productRequest.categoryId())
                .build();

        return productRepository.save(product)
                .doOnSuccess(savedProduct -> {
                    log.info("Product Created Successfully: {}", savedProduct.getId());
                    productProducer.sendProductCreatedEvent(new ProductResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice(), savedProduct.getCategoryId()));
                })
                .map(savedProduct -> new ProductResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice(), savedProduct.getCategoryId()));
    }

    @Override
    public Flux<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .doOnComplete(() -> log.info("Retrieved all products"))
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategoryId()));
    }

    @Override
    public Mono<ProductResponse> getProductById(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with id: " + id)))
                .doOnSuccess(product -> log.info("Retrieved product: {}", product.getId()))
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategoryId()));
    }

    @Override
    public Mono<ProductResponse> updateProduct(String id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found with id: " + id)))
                .flatMap(existingProduct -> {
                    existingProduct.setName(productRequest.name());
                    existingProduct.setDescription(productRequest.description());
                    existingProduct.setPrice(productRequest.price());
                    existingProduct.setCategoryId(productRequest.categoryId()); // Update categoryId
                    return productRepository.save(existingProduct);
                })
                .doOnSuccess(updatedProduct -> log.info("Product Updated Successfully: {}", updatedProduct.getId()))
                .map(updatedProduct -> new ProductResponse(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice(), updatedProduct.getCategoryId()));
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return productRepository.deleteById(id)
                                .doOnSuccess(voidValue -> log.info("Product Deleted Successfully: {}", id));
                    } else {
                        return Mono.error(new ProductNotFoundException("Product not found with id: " + id));
                    }
                });
    }
}