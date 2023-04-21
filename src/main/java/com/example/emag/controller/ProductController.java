package com.example.emag.controller;

import com.example.emag.model.DTOs.product.ProductAddDTO;
import com.example.emag.model.DTOs.product.ProductQtyChangeDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController extends AbstractController{

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id:\\d+}")
    public ProductViewDTO viewProductById(@PathVariable int id, HttpSession s){
        if(isLogged(s)){
            return productService.userViewProductById(id,getLoggedId(s));
        }
        return productService.viewProductById(id);
    }
    @PostMapping("/products")
    public ProductViewDTO addProduct(@Valid @RequestBody ProductAddDTO p){
        return productService.addProduct(p);
    }
    @DeleteMapping("products/{id:\\d+}")
    public ProductViewDTO deleteProductById(@PathVariable int id) {
        return productService.deleteProductById(id);
    }
    @PutMapping("/products/{id:\\d+}/quantity")
    public ProductViewDTO changeProductQty(@Valid @PathVariable int id, @RequestBody ProductQtyChangeDTO dto) {
        return productService.changeProductQty(id,dto.getQuantity());
    }

    @PutMapping("/products/{id:\\d+}")
    public ProductViewDTO editProductDetails(@PathVariable int id){
//        return productService.editProductDetails(id);
        //TODO
        return null;
    }

    @GetMapping("/products/search")
    public List<ProductViewDTO> searchByName(@RequestParam String name){
        return productService.searchByName(name);
    }

    @GetMapping("/categories/{id:\\d+}/products")
    public List<ProductViewDTO> viewAllProductsByCategoryId(@PathVariable int id){
        return productService.viewAllProductsByCategoryId(id);
    }

}
