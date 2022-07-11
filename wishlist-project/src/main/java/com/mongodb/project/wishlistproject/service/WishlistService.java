package com.mongodb.project.wishlistproject.service;

import com.mongodb.project.wishlistproject.controller.dto.ProductResponseDTO;
import com.mongodb.project.wishlistproject.controller.dto.WishlistResponseDTO;

import java.util.List;

public interface WishlistService {

    WishlistResponseDTO addProduct(String idProduct, String idUser);

    ProductResponseDTO removeProduct(String idProduct, String idUser);

    List<ProductResponseDTO> getAllProducts(String idUser);

    ProductResponseDTO getProduct(String idProduct, String idUser);
}
