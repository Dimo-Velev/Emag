package com.example.emag.controller;


import com.example.emag.model.DTOs.CardDTO;
import com.example.emag.model.DTOs.CardWithFewInfoDTO;
import com.example.emag.service.CardService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class CardController extends AbstractController {

    @Autowired
    private CardService cardService;

    @GetMapping("/cards")
    public List<CardWithFewInfoDTO> getAll(HttpSession session) {
        return cardService.getAllCards(getLoggedId(session));
    }

    @PostMapping("/cards")
    public CardWithFewInfoDTO add(@Valid @RequestBody CardDTO dto, HttpSession session) {
        return cardService.addCard(dto, getLoggedId(session));
    }

    @DeleteMapping("/cards/{id}")
    public String delete(@PathVariable int id, HttpSession session) {
        isLogged(session);
        cardService.deleteCard(id);
        return "Card is deleted";
    }

}
