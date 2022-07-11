package com.mongodb.project.wishlistproject.service.impl;

import com.mongodb.project.wishlistproject.controller.dto.ProductResponseDTO;
import com.mongodb.project.wishlistproject.exceptions.EmptyListException;
import com.mongodb.project.wishlistproject.exceptions.ExistsOnList;
import com.mongodb.project.wishlistproject.exceptions.FullListException;
import com.mongodb.project.wishlistproject.exceptions.NotFoundException;
import com.mongodb.project.wishlistproject.model.Products;
import com.mongodb.project.wishlistproject.repository.ProductRepository;
import com.mongodb.project.wishlistproject.repository.UserRepository;
import com.mongodb.project.wishlistproject.repository.WishlistRepository;
import com.mongodb.project.wishlistproject.service.WishlistService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WishlistServiceImplTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishlistService wishlistService;

    @Test
    @DisplayName("addProduct(), funciona corretamente")
    void addProduct() {

        assertNotNull(
                wishlistService.addProduct(
//                        O Produto deve ser um que não esteja na lista do usuário
                        "62c75097f98719fb25f78be1",
                        "62c7a1f39aee0ed79b1f955c"
                )
        );

    }

    @Test
    @DisplayName("addProduct(), adicionando produto inválido")
    void addProduct_invalidProduct() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            wishlistService.addProduct("62c75097f98719fb25f78bf", "62c7a1f39aee0ed79b1f955d");
        });

        assertEquals("Produto inválido", exception.getMessage());
    }

    @Test
    @DisplayName("addProduct(), adicionando para um usuário inválido")
    void addProduct_invalidUser() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            wishlistService.addProduct("62c75097f98719fb25f78", "62c7a1f39aee0ed79b1f9");
        });

        assertEquals("Usuário inválido", exception.getMessage());
    }

    @Test
    @DisplayName("addProduct(), adicionando um produto que já existe na lista")
    void addProduct_alreadyExistsOnList() {
        ExistsOnList exception = assertThrows(ExistsOnList.class, () -> wishlistService.addProduct(
                "62c75097f98719fb25f78bf4",
                "62c7a1f39aee0ed79b1f955d"
        ));

        assertEquals("Este produto já se encontra na sua lista de desejo", exception.getMessage());
    }

    @Test
    @DisplayName("addProduct(), adicionando um produto com a lista cheia")
    void addProduct_fullList() {
        FullListException exception = assertThrows(FullListException.class, () -> {
            wishlistService.addProduct(
//                    Utilizar o id de um produto válido (Não existente em sua lista de desejos)
                    "62c75097f98719fb25f78bf3",
//                    Utilizar o id de um usuário que está com 20 produtos na lista
                    "62c7a1f39aee0ed79b1f955b"
            );
        });

        assertEquals("Não é possível adicionar mais produtos na lista de desejos, lista cheia", exception.getMessage());
    }

    @Test
    @DisplayName("removeProduct(), removendo um produto")
    void removeProduct() {

        assertNotNull(wishlistService.removeProduct(
                "62c75097f98719fb25f78bf4",
                "62c7a1f39aee0ed79b1f955c"
        ));

    }

    @Test
    @DisplayName("removeProduct(), removendo um produto com a lista vazia")
    void removeProduct_emptyList() {

        assertThrows(EmptyListException.class, () -> {
            wishlistService.removeProduct(
                    "62c75097f98719fb25f78bf4",
                    "62c7a1f39aee0ed79b1f955d"
            );
        });

    }

    @Test
    @DisplayName("removeProduct(), removendo um produto inválido")
    void removeProduct_invalidProduct() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            wishlistService.removeProduct(
                    "62c75097f98719fb25f78b",
                    "62c7a1f39aee0ed79b1f955d");
        });

        assertEquals("Produto inválido", exception.getMessage());
    }

    @Test
    @DisplayName("getAllProducts(), buscando produtos da lista")
    void getAllProducts() {
        assertNotNull(wishlistService.getAllProducts("62c7a1f39aee0ed79b1f955d"));
    }

    @Test
    @DisplayName("getProduct(), buscando um único produto da lista")
    void getProduct() {

        Products p = productRepository.findById("62c75097f98719fb25f78bde").get();

        ProductResponseDTO result = wishlistService.getProduct(
                "62c75097f98719fb25f78bdf",
                "62c7a1f39aee0ed79b1f955b"
        );

    }

    @Test
    @DisplayName("getProduct(), buscando um único produto da lista")
    void getProduct_invalidProduct() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            wishlistService.getProduct(
                    "62c75097f98719fb25f78b",
                    "62c7a1f39aee0ed79b1f955d");
        });

        assertEquals("Produto não encontrado", exception.getMessage());
    }
}