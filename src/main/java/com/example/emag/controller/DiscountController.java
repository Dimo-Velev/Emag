package com.example.emag.controller;

import com.example.emag.model.DTOs.discount.DiscountAddDTO;
import com.example.emag.model.DTOs.discount.DiscountViewDTO;
import com.example.emag.model.entities.Discount;
import com.example.emag.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiscountController extends AbstractController{

    @Autowired
    private DiscountService discountService;


    @GetMapping("/discounts/{id}")
    public DiscountViewDTO viewDiscountById(@PathVariable int id) {
        return discountService.viewDiscountById(id);
    }

    @PostMapping("/discounts")
    public DiscountViewDTO addDiscount(@Valid @RequestBody DiscountAddDTO d) {
        return discountService.addDiscount(d);
    }

    @PutMapping("/discounts/{id}")
    public DiscountViewDTO updateDiscount(@Valid @PathVariable int id, @RequestBody DiscountAddDTO dto) {
        return discountService.updateDiscount(id, dto);
    }

    @DeleteMapping("/discounts/{id}")
    public DiscountViewDTO deleteDiscount(@PathVariable int id) {
        return discountService.deleteDiscountById(id);
    }

    @GetMapping("/discounts")
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }


}
