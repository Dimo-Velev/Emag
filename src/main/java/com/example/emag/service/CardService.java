package com.example.emag.service;

import com.example.emag.model.DTOs.CardDTO;
import com.example.emag.model.DTOs.CardWithFewInfoDTO;
import com.example.emag.model.entities.Card;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;


public class CardService extends AbstractService {

    @Autowired
    private CardRepository cardRepository;

    public List<CardWithFewInfoDTO> getAllCards(int id) {
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
                .map(card -> {
                    CardWithFewInfoDTO cDTO = mapper.map(card, CardWithFewInfoDTO.class);
                    cDTO.setMaskedCardNumber("****" + card.getCardNumber().substring(card.getCardNumber().length() - 4));
                    return cDTO;
                })
                .toList();
    }

    public CardWithFewInfoDTO addCard(CardDTO dto, int id) {
        if (cardRepository.getCardByCardNumberAndUser(dto.getCardNumber(), getUserById(id)).isPresent()) {
            throw new BadRequestException("Card is already registered in this user account.");
        }
        if (isExpired(dto)) {
            throw new BadRequestException("Card is expired.");
        }
        Card card = cardRepository.save(mapper.map(dto, Card.class));
        return mapper.map(card, CardWithFewInfoDTO.class);
    }

    public String deleteCard(int id){
        cardRepository.deleteById(id);
        return "Card was deleted.";
    }

    public boolean isExpired(CardDTO dto) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        return (dto.getExpireYear() < currentYear) || (dto.getExpireYear() == currentYear && dto.getExpireMonth() <= currentMonth);
    }
}