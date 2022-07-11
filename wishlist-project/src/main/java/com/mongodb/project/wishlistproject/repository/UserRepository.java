package com.mongodb.project.wishlistproject.repository;

import com.mongodb.project.wishlistproject.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
