package com.example.emag.controller;

import com.example.emag.model.DTOs.product.ProductAddDTO;
import com.example.emag.model.DTOs.product.ProductEditDTO;
import com.example.emag.model.DTOs.product.ProductQuantityDTO;
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
    public ProductViewDTO addProduct(@Valid @RequestBody ProductAddDTO p, HttpSession s) {
        isLoggedAdmin(s);
        return productService.addProduct(p);
    }

    @DeleteMapping("products/{id:\\d+}")
    public ProductViewDTO deleteProductById(@PathVariable int id, HttpSession s) {
        isLoggedAdmin(s);
        return productService.deleteProductById(id);
    }

    @PutMapping("/products/{id:\\d+}/quantity")
    public ProductViewDTO changeProductQty(@Valid @PathVariable int id, @RequestBody ProductQuantityDTO dto, HttpSession s) {
        isLoggedAdmin(s);
        return productService.changeProductQty(id, dto.getQuantity());
    }

    @PutMapping("/products/{id:\\d+}")
    public ProductViewDTO editProductDetails(@PathVariable int id, @Valid @RequestBody ProductEditDTO dto, HttpSession s) {
        isLoggedAdmin(s);
        return productService.editProductDetails(id, dto);
    }

    @GetMapping("/products/search")
    public Page<ProductViewDTO> searchByName(@RequestParam String name, Pageable pageable) {
        return productService.searchByName(name, pageable);
    }

    @GetMapping("/categories/{id:\\d+}/products")
    public Page<ProductViewDTO> filterProducts(@PathVariable int id, Pageable pageable) {
        return productService.viewAllProductsByCategoryId(pageable, id);
    }

    @GetMapping("/products/filter")
    public Page<ProductViewDTO> filterProducts(@RequestParam int min,
                                               @RequestParam int max,
                                               @RequestParam(required = false) boolean desc,
                                               Pageable pageable) {
        return productService.filterProducts(min, max, desc, pageable);
    }


}
