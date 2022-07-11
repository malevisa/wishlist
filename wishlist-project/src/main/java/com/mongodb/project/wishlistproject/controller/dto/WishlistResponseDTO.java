package com.mongodb.project.wishlistproject.controller.dto;

import java.util.List;

public class WishlistResponseDTO {

    private List<ProductResponseDTO> products;
    private UserResponseDTO user;

    public WishlistResponseDTO(List<ProductResponseDTO> products, UserResponseDTO user) {
        this.products = products;
        this.user = user;
    }

    public List<ProductResponseDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponseDTO> products) {
        this.products = products;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}
