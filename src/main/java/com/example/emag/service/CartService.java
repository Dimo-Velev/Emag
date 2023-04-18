package com.example.emag.service;

import com.example.emag.model.DTOs.cart.CartContentDTO;
import com.example.emag.model.DTOs.cart.ProductInCartDTO;
import com.example.emag.model.DTOs.product.ProductQuantityDTO;
import com.example.emag.model.DTOs.user.UserWithCartDTO;
import com.example.emag.model.entities.CartContent;
import com.example.emag.model.entities.CartContentKey;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CartService extends AbstractService {

    public CartContentDTO getCartContent(int id) {
        UserWithCartDTO dto = mapper.map(getUserById(id), UserWithCartDTO.class);
        return mapper.map(dto, CartContentDTO.class);
    }

    public ProductInCartDTO addProductToCart(int id, int userId) {
        CartContent cartContentInDB = cartContentRepository.findByProductIdAndUserId(id, userId);
        if (cartContentInDB == null) {
            CartContentKey compositeKey = createCompositeKey(id, userId);
            CartContent cartContent = createCartContent(id, userId, compositeKey);
            cartContentRepository.save(cartContent);
            return createDTO(cartContent);
        } else {
            cartContentInDB.setQuantity(cartContentInDB.getQuantity() + 1);
            cartContentRepository.save(cartContentInDB);
            return createDTO(cartContentInDB);
        }
    }

    private void checkIfValidQuantity(int quantity) {
        if (quantity <= 0 || quantity > 100) {
            throw new BadRequestException("Quantity must be between 1 and 100.");
        }
    }

    public ProductInCartDTO editQuantityOfProductInCart(int id, ProductQuantityDTO dto, int userId) {
        checkIfValidQuantity(dto.getQuantity());
        CartContent cartContent = cartContentRepository.findByProductIdAndUserId(id, userId);
        if (cartContent == null) {
            throw new NotFoundException("Product not found in cart.");
        }
        cartContent.setQuantity(dto.getQuantity());
        ProductInCartDTO product = new ProductInCartDTO();
        product.setPrice(getProductPrice(cartContent.getProduct().getId()));
        product.setName(cartContent.getProduct().getName());
        product.setQuantity(dto.getQuantity());
        cartContentRepository.save(cartContent);
        return product;
    }

    public String removeProductFromCart(int id, int userId) {
        CartContent cartContent = cartContentRepository.findByProductIdAndUserId(id, userId);
        if (cartContent == null) {
            throw new NotFoundException("Product not found in cart.");
        }
        cartContentRepository.deleteById(cartContent.getId());
        return "Product was removed from the cart.";
    }

    private CartContentKey createCompositeKey(int id, int userId) {
        CartContentKey compositeKey = new CartContentKey();
        compositeKey.setUserId(userId);
        compositeKey.setProductId(id);
        return compositeKey;
    }

    private CartContent createCartContent(int id, int userId, CartContentKey key) {
        User user = getUserById(userId);
        Product product = getProductById(id);
        CartContent cartContent = new CartContent();
        cartContent.setUser(user);
        cartContent.setProduct(product);
        cartContent.setQuantity(1);
        cartContent.setId(key);
        return cartContent;
    }

    private ProductInCartDTO createDTO(CartContent cartContent) {
        ProductInCartDTO dto = new ProductInCartDTO();
        dto.setName(cartContent.getProduct().getName());
        dto.setQuantity(cartContent.getQuantity());
        dto.setPrice(cartContent.getProduct().getPrice());
        dto.setDiscount(cartContent.getProduct().getDiscount().getDiscountPercent() + "%");
        return dto;
    }
}
