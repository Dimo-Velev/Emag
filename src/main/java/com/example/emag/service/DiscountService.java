package com.example.emag.service;

import com.example.emag.model.DTOs.discount.DiscountAddDTO;
import com.example.emag.model.DTOs.discount.DiscountAddToProductDTO;
import com.example.emag.model.DTOs.discount.DiscountViewDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.entities.Discount;
import com.example.emag.model.entities.Product;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.repositories.DiscountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DiscountService extends AbstractService{

    @Autowired
    private DiscountRepository discountRepository;


    protected Discount getDiscountById(int id){
        return discountRepository.findById(id).orElseThrow(() -> new NotFoundException("Discount not found"));
    }

    public DiscountViewDTO viewDiscountById(int id) {
        Discount d = getDiscountById(id);
        return mapper.map(d, DiscountViewDTO.class);
    }

    public DiscountViewDTO addDiscount(DiscountAddDTO d) {
        if(d.getExpireDate().isBefore(d.getStartDate())){
            throw new BadRequestException("Expiry date cannot be before start date");
        }
        if(d.getExpireDate().isBefore(LocalDateTime.now())){
            throw new BadRequestException("Expiry date cannot be before today");
        }
        Discount discount = mapper.map(d,Discount.class);
        discountRepository.save(discount);
        return mapper.map(discount,DiscountViewDTO.class);
    }

    @Transactional
    public DiscountViewDTO deleteDiscountById(int id) {
        Discount d = getDiscountById(id);
        DiscountViewDTO dto = mapper.map(d,DiscountViewDTO.class);
        discountRepository.deleteById(id);
        return dto;
    }

    public Page<DiscountViewDTO> getAllDiscounts(Pageable pageable) {
        Page<Discount> discountPage = discountRepository.findAll(pageable);
        if (discountPage.isEmpty()) {
            throw new NotFoundException("No discounts were found");
        }
        return discountPage.map(discount -> mapper.map(discount, DiscountViewDTO.class));
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


    public ProductViewDTO addDiscountToProduct(DiscountAddToProductDTO dto) {
        Discount d = getDiscountById(dto.getDiscountId());
        Product p = getProductById(dto.getProductId());
        if(d.getExpireDate().isBefore(LocalDateTime.now())){
            throw new BadRequestException("Provided discount is expired");
        }
        p.setDiscount(d);
        productRepository.save(p);
        sendEmail(p);
        return mapper.map(p,ProductViewDTO.class);
    }



}
