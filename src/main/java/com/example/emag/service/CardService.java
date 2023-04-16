package com.example.emag.service;

import com.example.emag.model.DTOs.card.CardDTO;
import com.example.emag.model.DTOs.card.CardWithFewInfoDTO;
import com.example.emag.model.entities.Card;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
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
        Card card = mapper.map(dto, Card.class);
        card.setUser(getUserById(id));
        cardRepository.save(card);
        return mapper.map(card, CardWithFewInfoDTO.class);
    }

    public String deleteCard(int id, int userId) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Card not found."));
        if (card.getUser().getId() != userId) {
            throw new UnauthorizedException("You have no access to this resource.");
        }
        cardRepository.deleteById(id);
        return "Card was deleted.";
    }

    public boolean isExpired(CardDTO dto) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        return (dto.getExpireYear() < currentYear) || (dto.getExpireYear() == currentYear && dto.getExpireMonth() <= currentMonth);
    }
}