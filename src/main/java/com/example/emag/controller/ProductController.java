package com.example.emag.controller;

import com.example.emag.model.DTOs.product.ProductAddDTO;
import com.example.emag.model.DTOs.product.ProductQtyChangeDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController extends AbstractController{

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    public ProductViewDTO viewProductById(@PathVariable int id){
        return productService.viewProductById(id);
    }
    @PostMapping("/products")
    public ProductViewDTO addProduct(@Valid @RequestBody ProductAddDTO p){
        return productService.addProduct(p);
    }
    @DeleteMapping("products/{id}")
    public ProductViewDTO deleteProductById(@PathVariable int id) {
        return productService.deleteProductById(id);
    }
    @PutMapping("/products/{id}/quantity")
    public ProductViewDTO changeProductQty(@Valid @PathVariable int id, @RequestBody ProductQtyChangeDTO dto) {
        return productService.changeProductQty(id,dto.getQuantity());
    }

    @PutMapping("/products/{id}")
    public ProductViewDTO editProductDetails(@PathVariable int id){
//        return productService.editProductDetails(id);
        //TODO
        return null;
    }



}
