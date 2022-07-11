package com.mongodb.project.wishlistproject.service.impl;

import com.mongodb.project.wishlistproject.controller.dto.ProductResponseDTO;
import com.mongodb.project.wishlistproject.controller.dto.UserResponseDTO;
import com.mongodb.project.wishlistproject.controller.dto.WishlistResponseDTO;
import com.mongodb.project.wishlistproject.exceptions.EmptyListException;
import com.mongodb.project.wishlistproject.exceptions.ExistsOnList;
import com.mongodb.project.wishlistproject.exceptions.FullListException;
import com.mongodb.project.wishlistproject.exceptions.NotFoundException;
import com.mongodb.project.wishlistproject.model.Products;
import com.mongodb.project.wishlistproject.model.User;
import com.mongodb.project.wishlistproject.model.Wishlist;
import com.mongodb.project.wishlistproject.repository.ProductRepository;
import com.mongodb.project.wishlistproject.repository.UserRepository;
import com.mongodb.project.wishlistproject.repository.WishlistRepository;
import com.mongodb.project.wishlistproject.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public WishlistResponseDTO addProduct(String idProduct, String idUser) {

        try {
            User user = checkValidUser(idUser);
            Products product = checkValidProduct(idProduct);
            Wishlist wishlist = verifyWishlistUser(user);

            wishlist.setProducts(addProductOnList(wishlist, product));
            wishlistRepository.save(wishlist);

            List<ProductResponseDTO> productResponseList = new ArrayList<>();

            for (Products p : wishlist.getProducts()) {
                ProductResponseDTO productResponse = new ProductResponseDTO(p.getNome(), p.getValor());

                productResponseList.add(productResponse);
            }

            return new WishlistResponseDTO(
                    productResponseList,
                    new UserResponseDTO(
                            user.getNome()
                    )
            );
        } catch (FullListException e) {
            throw new FullListException(e.getMessage());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (ExistsOnList e) {
            throw new ExistsOnList(e.getMessage());
        }
    }

    @Override
    public ProductResponseDTO removeProduct(String idProduct, String idUser) {
        try {
            User user = checkValidUser(idUser);
            Products product = checkValidProduct(idProduct);
            Wishlist wishlist = verifyWishlistUser(user);

            wishlist.setProducts(removeProductInList(wishlist, product));

            wishlistRepository.save(wishlist);

            return new ProductResponseDTO(
                    product.getNome(),
                    product.getValor()
            );
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (EmptyListException e) {
            throw new EmptyListException(e.getMessage());
        }
    }

    @Override
    public List<ProductResponseDTO> getAllProducts(String idUser) {

        try {

            User user = checkValidUser(idUser);

            Wishlist wishlist = verifyWishlistUser(user);

            List<ProductResponseDTO> productResponseList = new ArrayList<>();

            for (Products p : wishlist.getProducts()) {
                ProductResponseDTO productResponse = new ProductResponseDTO(p.getNome(), p.getValor());

                productResponseList.add(productResponse);
            }

            return productResponseList;

        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }

    }

    @Override
    public ProductResponseDTO getProduct(String idProduct, String idUser) {

        User user = checkValidUser(idUser);
        Wishlist wishlist = verifyWishlistUser(user);

        for (Products p : wishlist.getProducts()) {
            if (p.getId().equals(idProduct)) {
                return new ProductResponseDTO(p.getNome(), p.getValor());
            }
        }

        throw new NotFoundException("Produto não encontrado");
    }

    public Wishlist verifyWishlistUser(User user) {
        Optional<Wishlist> wishlistUser = wishlistRepository.findByUser(user.getId());

        return wishlistUser.orElse(new Wishlist(user));
    }

    public List<Products> addProductOnList(Wishlist wishlist, Products product) {

        if (!checkFullList(wishlist)) {
            List<Products> products = wishlist.getProducts();
            if (products != null) {
                for (Products p : products) {
                    if (p.equals(product)) {
                        throw new ExistsOnList("Este produto já se encontra na sua lista de desejo");
                    }
                }
                products.add(product);
            }
            return products;
        }

        throw new FullListException("Não é possível adicionar mais produtos na lista de desejos, lista cheia");

    }

    public boolean checkFullList(Wishlist wishlist) {
        return wishlist.getProducts().size() == 20;
    }

    public List<Products> removeProductInList(Wishlist wishlist, Products product) {
        List<Products> products = wishlist.getProducts();

        if (!products.isEmpty()) {
            for (Products p : products) {
                if (p.equals(product)) {
                    products.remove(product);
                    return products;
                }
            }
            throw new NotFoundException("Produto não encontrado!");
        }
        throw new EmptyListException("Lista de desejos vazia!");
    }

    public User checkValidUser(String idUser) {
        Optional<User> user = userRepository.findById(idUser);
        if (user.isEmpty()) {
            throw new NotFoundException("Usuário inválido");
        }
        return user.get();
    }

    public Products checkValidProduct(String idProduct) {
        Optional<Products> product = productRepository.findById(idProduct);

        if (product.isEmpty()) {
            throw new NotFoundException("Produto inválido");
        }
        return product.get();
    }

}
