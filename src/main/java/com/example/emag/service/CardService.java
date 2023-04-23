package com.example.emag.service;

import com.example.emag.model.DTOs.card.CardDTO;
import com.example.emag.model.DTOs.card.CardWithFewInfoDTO;
import com.example.emag.model.entities.Card;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardService extends AbstractService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TextEncryptor textEncryptor;

    public List<CardWithFewInfoDTO> getAllCards(int id) {
        User user = getUserById(id);
        List<Card> cards = user.getCards();
        if (cards.isEmpty()){
            throw new NotFoundException("No registered cards.");
        }
        return cards.stream()
                .map(card -> {
                    CardWithFewInfoDTO cDTO = new CardWithFewInfoDTO();
                    cDTO.setCardNumber(textEncryptor.decrypt(card.getCardNumber()));
                    cDTO.setName(textEncryptor.decrypt(card.getName()));
                    cDTO.setId(card.getId());
                    cDTO.setExpireMonth(textEncryptor.decrypt(card.getExpireMonth()));
                    cDTO.setExpireYear(textEncryptor.decrypt(card.getExpireYear()));
                    cDTO.setCvv(textEncryptor.decrypt(card.getCvvCode()));
                    return cDTO;
                })
                .toList();
    }

    public CardWithFewInfoDTO addCard(CardDTO dto, int id) {
        User user = getUserById(id);
        boolean isCardRegistered = user.getCards().stream()
                .anyMatch(card -> dto.getCardNumber().equals(textEncryptor.decrypt(card.getCardNumber())));
        if (isCardRegistered) {
            throw new BadRequestException("Card already registered");
        }
        if (isExpired(dto)) {
            throw new BadRequestException("Card is expired.");
        }
        Card card = new Card();
        card.setCardNumber(textEncryptor.encrypt(dto.getCardNumber()));
        card.setName(textEncryptor.encrypt(dto.getName()));
        card.setExpireMonth(textEncryptor.encrypt(dto.getExpireMonth()));
        card.setExpireYear(textEncryptor.encrypt(dto.getExpireYear()));
        card.setCvvCode(textEncryptor.encrypt(dto.getCvvCode()));
        card.setUser(user);
        cardRepository.save(card);
        return new CardWithFewInfoDTO();
    }


    public void deleteCard(int id, int userId) {
        User user = getUserById(id);

        Card card = cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Card not found."));
        if (card.getUser().getId() != userId) {
            throw new UnauthorizedException("You have no access to this resource.");
        }
        cardRepository.deleteById(id);
    }

    public boolean isExpired(CardDTO dto) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int expireYear = Integer.parseInt(dto.getExpireYear());
        int expireMonth = Integer.parseInt(dto.getExpireMonth());
        return (expireYear < currentYear) || (expireYear == currentYear && expireMonth <= currentMonth);
    }
}