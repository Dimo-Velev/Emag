package com.example.emag.controller;

import com.example.emag.model.DTOs.discount.DiscountAddDTO;
import com.example.emag.model.DTOs.discount.DiscountAddToProductDTO;
import com.example.emag.model.DTOs.discount.DiscountViewDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.service.DiscountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
public class DiscountController extends AbstractController{

    @Autowired
    private DiscountService discountService;


    @GetMapping("/discounts/{id:\\d+}")
    public DiscountViewDTO viewDiscountById(@PathVariable int id, HttpSession s) {
        isLoggedAdmin(s);
        return discountService.viewDiscountById(id);
    }

    @PostMapping("/discounts")
    public DiscountViewDTO addDiscount(@Valid @RequestBody DiscountAddDTO d, HttpSession s) {
        isLoggedAdmin(s);
        return discountService.addDiscount(d);
    }

    @PutMapping("/discounts/{id:\\d+}")
    public DiscountViewDTO updateDiscount(@Valid @PathVariable int id, @RequestBody DiscountAddDTO dto, HttpSession s) {
        isLoggedAdmin(s);
        return discountService.updateDiscount(id, dto);
    }

    @DeleteMapping("/discounts/{id:\\d+}")
    public DiscountViewDTO deleteDiscount(@PathVariable int id, HttpSession s) {
        isLoggedAdmin(s);
        return discountService.deleteDiscountById(id);
    }

    @GetMapping("/discounts")
    public Page<DiscountViewDTO> getAllDiscounts(Pageable pageable, HttpSession s) {
        isLoggedAdmin(s);
        return discountService.getAllDiscounts(pageable);
    }


    @PutMapping("/discounts/products")
    public ProductViewDTO addDiscountToProduct(@Valid @RequestBody DiscountAddToProductDTO dto, HttpSession s){
        isLoggedAdmin(s);
        return discountService.addDiscountToProduct(dto);
    }





}
