package com.example.emag.controller;

import com.example.emag.model.DTOs.FavoriteProductDTO;
import com.example.emag.service.FavoritesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FavoritesController extends AbstractController {

    @Autowired
    private FavoritesService favouritesService;


    @GetMapping("/favourites")
    public List<FavoriteProductDTO> getFavourites(HttpSession session){
        return favouritesService.getFavouritesProducts(getLoggedId(session));
    }
}
