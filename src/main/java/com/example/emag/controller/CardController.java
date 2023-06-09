package com.example.emag.controller;


import com.example.emag.model.DTOs.card.CardDTO;
import com.example.emag.model.DTOs.card.CardWithFewInfoDTO;
import com.example.emag.model.DTOs.card.CardWithIdDTO;
import com.example.emag.service.CardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CardWithIdDTO> add(@RequestBody CardDTO dto, HttpSession session) {
        CardWithIdDTO idDTO = cardService.addCard(dto, getLoggedId(session));
        return  ResponseEntity.ok(idDTO);
    }


    @DeleteMapping("/cards/{id:\\d+}")
    public ResponseEntity<String> delete(@PathVariable int id, HttpSession session) {
        cardService.deleteCard(id, getLoggedId(session));
        return ResponseEntity.ok("Card was deleted.");
    }
}
