package com.mongodb.project.wishlistproject.repository;

import com.mongodb.project.wishlistproject.model.Products;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Products, String> {
}
