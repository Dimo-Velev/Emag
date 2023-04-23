package com.example.emag.service;

import com.example.emag.model.DTOs.product.ProductFavoritesDTO;
import com.example.emag.model.DTOs.product.ProductReactDTO;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return products.stream().
                map(product -> mapper.map(product, ProductFavoritesDTO.class))
                .collect(Collectors.toList());
    }

    public ProductReactDTO addOrRemoveProductFavorites(int id, int userId) {
        User user = getUserById(userId);
        Product product = getProductById(id);
        Set<Product> products = user.getFavoriteProducts();
        String message = "";
        boolean isPresent = products.stream()
                .anyMatch(product1 -> product1.getId() == product.getId());
        if (isPresent) {
            products.remove(product);
            message = "Product removed from favorites.";
        } else {
            products.add(product);
            message = "Product added to favorites.";
        }
        userRepository.save(user);
        ProductReactDTO dto = mapper.map(product, ProductReactDTO.class);
        dto.setMessage(message);
        Optional<Integer> rating = product.getReviews().stream()
                .map(review -> review.getRating())
                .reduce((rating1, total) -> rating1 + total);
        rating.ifPresent(integer -> dto.setRating(rating.get()));
//        dto.setDiscount(product.getDiscount().getDiscountPercent());
        return dto;
    }
}

