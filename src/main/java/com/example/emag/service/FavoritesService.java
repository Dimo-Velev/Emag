package com.example.emag.service;

import com.example.emag.model.DTOs.product.ProductFavoritesDTO;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FavoritesService extends AbstractService {

    public List<ProductFavoritesDTO> getFavouritesProducts(int id) {
        User user = getUserById(id);
        Set<Product> products = user.getFavoriteProducts();
        if (products == null) {
            throw new NotFoundException("You have no favorite products.");
        }
        return products.stream().map(product -> {
                    // TODO ADD A REVIEW RATING HERE
                    return mapper.map(product, ProductFavoritesDTO.class);
                })
                .collect(Collectors.toList());
    }

    public ResponseEntity<ProductFavoritesDTO> addOrRemoveProductFavorites(int id, int userId) {
        User user = getUserById(userId);
        Product product = getProductById(id);
        Set<Product> products = user.getFavoriteProducts();
        boolean isPresent = products.stream()
                .anyMatch(product1 -> product1.getId() == product.getId());
        HttpHeaders headers = new HttpHeaders();
        if (isPresent) {
            products.remove(product);
            headers.add("Removed", "Product removed from favorites.");
        } else {
            products.add(product);
            headers.add("Added", "Product added to favorites.");
        }
        userRepository.save(user);
        ProductFavoritesDTO dto = mapper.map(product, ProductFavoritesDTO.class);
        return ResponseEntity.ok().headers(headers).body(dto);
    }
}

