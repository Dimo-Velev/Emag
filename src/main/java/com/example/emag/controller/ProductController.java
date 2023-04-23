package com.example.emag.controller;

import com.example.emag.model.DTOs.product.ProductAddDTO;
import com.example.emag.model.DTOs.product.ProductEditDTO;
import com.example.emag.model.DTOs.product.ProductQtyChangeDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController extends AbstractController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id:\\d+}")
    public ProductViewDTO viewProductById(@PathVariable int id, HttpSession s) {
        if (isLogged(s)) {
            return productService.userViewProductById(id, getLoggedId(s));
        }
        return productService.viewProductById(id);
    }

    @PostMapping("/products")
    public ProductViewDTO addProduct(@Valid @RequestBody ProductAddDTO p) {
        return productService.addProduct(p);
    }

    @DeleteMapping("products/{id:\\d+}")
    public ProductViewDTO deleteProductById(@PathVariable int id) {
        return productService.deleteProductById(id);
    }

    @PutMapping("/products/{id:\\d+}/quantity")
    public ProductViewDTO changeProductQty(@Valid @PathVariable int id, @RequestBody ProductQtyChangeDTO dto) {
        return productService.changeProductQty(id, dto.getQuantity());
    }

    @PutMapping("/products/{id:\\d+}")
    public ProductViewDTO editProductDetails(@PathVariable int id, @Valid @RequestBody ProductEditDTO dto) {
        return productService.editProductDetails(id, dto);
    }

    @GetMapping("/products/search")
    public Page<ProductViewDTO> searchByName(@RequestParam String name, Pageable pageable) {
        return productService.searchByName(name, pageable);
    }

    @GetMapping("/categories/{id:\\d+}/products")
    public Page<ProductViewDTO> viewAllProductsByCategoryId(@PathVariable int id, Pageable pageable) {
        return productService.viewAllProductsByCategoryId(pageable, id);
    }

}
