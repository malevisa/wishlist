package com.mongodb.project.wishlistproject.repository;

import com.mongodb.project.wishlistproject.model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    @Query("{'user._id': ObjectId('?0')}")
    Optional<Wishlist> findByUser(String idUser);

}
