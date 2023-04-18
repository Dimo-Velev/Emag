package com.example.emag.service;

import com.example.emag.model.DTOs.discount.DiscountAddDTO;
import com.example.emag.model.DTOs.discount.DiscountViewDTO;
import com.example.emag.model.entities.Discount;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.repositories.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService extends AbstractService{

    @Autowired
    private DiscountRepository discountRepository;

    public DiscountViewDTO viewDiscountById(int id) {
        Discount d = getDiscountById(id);
        return mapper.map(d, DiscountViewDTO.class);
    }

    public DiscountViewDTO addDiscount(DiscountAddDTO d) {
        Discount discount = mapper.map(d,Discount.class);
        discountRepository.save(discount);
        return mapper.map(discount,DiscountViewDTO.class);
    }

    public DiscountViewDTO deleteDiscountById(int id) {
        Discount d = getDiscountById(id);
        DiscountViewDTO dto = mapper.map(d,DiscountViewDTO.class);
        discountRepository.deleteById(id);
        return dto;
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }
    public DiscountViewDTO updateDiscount(int id, DiscountAddDTO dto) {
        Discount d = getDiscountById(id);
        if(dto.getExpireDate().isBefore(dto.getStartDate())){
            throw new BadRequestException("Expiry date cannot be before start date");
        }
        d.setDiscountPercent(dto.getDiscountPercent());
        d.setExpireDate(dto.getExpireDate());
        d.setExpireDate(dto.getStartDate());
        discountRepository.save(d);
        return mapper.map(d, DiscountViewDTO.class);
    }



}
