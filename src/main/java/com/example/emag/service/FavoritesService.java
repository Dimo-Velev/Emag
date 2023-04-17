package com.example.emag.service;

import com.example.emag.model.DTOs.FavoriteProductDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoritesService extends AbstractService {

    public List<FavoriteProductDTO> getFavouritesProducts(int id) {
        //TODO
        return new ArrayList<>();
    }
}

