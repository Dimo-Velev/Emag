package com.example.emag.controller;

import com.example.emag.model.DTOs.product.ProductFavoritesDTO;
import com.example.emag.model.DTOs.product.ProductReactDTO;
import com.example.emag.service.FavoritesService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FavoritesController extends AbstractController {

    @Autowired
    private FavoritesService favouritesService;


    @GetMapping("/favorites")
    public List<ProductFavoritesDTO> getFavourites(HttpSession session){
        return favouritesService.getFavouritesProducts(getLoggedId(session));
    }
    @PostMapping("/favorites/products/{id:\\d+}")
    public ProductReactDTO likeDislikeProduct(@Valid @PathVariable int id, HttpSession session){
       return favouritesService.addOrRemoveProductFavorites(id,getLoggedId(session));
    }
}
