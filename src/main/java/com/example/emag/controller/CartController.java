package com.example.emag.controller;

import com.example.emag.model.DTOs.cart.CartContentDTO;
import com.example.emag.model.DTOs.cart.ProductInCartDTO;
import com.example.emag.model.DTOs.product.ProductQuantityDTO;
import com.example.emag.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class CartController extends AbstractController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public CartContentDTO getContent(HttpSession session) {
        return cartService.getCartContent(getLoggedId(session));
    }

    @PostMapping("/cart/products/{id}")
    public ProductInCartDTO addProduct(@PathVariable int id, HttpSession session) {
        return cartService.addProductToCart(id,getLoggedId(session));
    }

    @PutMapping("/cart/products/{id}/quantity")
    public ProductInCartDTO editQuantityOfProduct(@PathVariable int id, @RequestBody ProductQuantityDTO dto, HttpSession session) {
        return cartService.editQuantityOfProductInCart(id, dto, getLoggedId(session));
    }

    @DeleteMapping("/cart/products/{id}")
    public String deleteProduct(@PathVariable int id, HttpSession session) {
        return cartService.removeProductFromCart(id, getLoggedId(session));
    }
}
