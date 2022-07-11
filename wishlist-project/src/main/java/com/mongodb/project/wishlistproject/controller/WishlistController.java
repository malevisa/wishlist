package com.mongodb.project.wishlistproject.controller;

import com.mongodb.project.wishlistproject.controller.dto.ProductResponseDTO;
import com.mongodb.project.wishlistproject.controller.dto.WishlistResponseDTO;
import com.mongodb.project.wishlistproject.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/{idProduct}/{idUser}")
    public ResponseEntity<WishlistResponseDTO> addProduct(@PathVariable String idProduct, @PathVariable String idUser) {
        return ResponseEntity.status(200).body(wishlistService.addProduct(idProduct, idUser));
    }

    @DeleteMapping("/{idProduct}/{idUser}")
    public ResponseEntity<ProductResponseDTO> removeProduct(@PathVariable String idProduct, @PathVariable String idUser) {
        return ResponseEntity.status(200).body(wishlistService.removeProduct(idProduct, idUser));
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(@PathVariable String idUser) {
        List<ProductResponseDTO> products = wishlistService.getAllProducts(idUser);
        if (products.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(products);
    }

    @GetMapping("/{idProduct}/{idUser}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable String idProduct, @PathVariable String idUser) {
        ProductResponseDTO product = wishlistService.getProduct(idProduct, idUser);

        return ResponseEntity.status(200).body(product);
    }
}
