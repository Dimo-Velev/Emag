package com.example.emag.service;

import com.example.emag.model.DTOs.cart.CartContentDTO;
import com.example.emag.model.DTOs.cart.ProductInCartDTO;
import com.example.emag.model.DTOs.product.ProductQuantityDTO;
import com.example.emag.model.entities.CartContent;
import com.example.emag.model.entities.CartContentKey;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService extends AbstractService {

    public CartContentDTO getCartContent(int id) {
        Set<CartContent> cart = getUserById(id).getProductsInCart();
        double totalPrice = 0.0;
        for (CartContent cartContent : cart) {
            totalPrice += cartContent.getProduct().getPrice() * cartContent.getQuantity();
        }
        List<ProductInCartDTO> cartContentDTOS = cart.stream()
                .map(cartContent -> {
                    ProductInCartDTO product = new ProductInCartDTO();
                    product.setName(cartContent.getProduct().getName());
                    product.setDiscount(cartContent.getProduct().getDiscount().getDiscountPercent() + "%");
                    product.setQuantity(cartContent.getQuantity());
                    product.setPrice(cartContent.getProduct().getPrice());
                    return product;
                })
                .toList();
        CartContentDTO dto = new CartContentDTO();
        dto.setTotalPrice(totalPrice);
        dto.setProducts(cartContentDTOS);
        return dto;
    }

    public ProductInCartDTO addProductToCart(int id, int userId) {
        Optional<CartContent> cartContentInDB = cartContentRepository.findByProductIdAndUserId(id, userId);
        if (cartContentInDB.isEmpty()) {
            CartContentKey compositeKey = createCompositeKey(id, userId);
            CartContent cartContent = createCartContent(id, userId, compositeKey);
            cartContentRepository.save(cartContent);
            return createDTO(cartContent);
        } else {
            CartContent cartContent = cartContentInDB.get();
            cartContent.setQuantity(cartContent.getQuantity() + 1);
            if (cartContent.getProduct().getQuantity() < cartContent.getQuantity()) {
                throw new BadRequestException("We don't have that much quantity of that product.");
            }
            cartContentRepository.save(cartContent);
            return createDTO(cartContent);
        }
    }

    private void checkIfValidQuantity(int quantity) {
        if (quantity <= 0 || quantity > 100) {
            throw new BadRequestException("Quantity must be between 1 and 100.");
        }
    }

    public ProductInCartDTO editQuantityOfProductInCart(int id, ProductQuantityDTO dto, int userId) {
        checkIfValidQuantity(dto.getQuantity());
        CartContent cartContent = cartContentRepository.findByProductIdAndUserId(id, userId).orElseThrow(() -> new BadRequestException("Cart is empty."));
        cartContent.setQuantity(dto.getQuantity());
        ProductInCartDTO product = new ProductInCartDTO();
        product.setPrice(getProductPrice(cartContent.getProduct().getId()));
        product.setName(cartContent.getProduct().getName());
        product.setQuantity(dto.getQuantity());
        cartContentRepository.save(cartContent);
        return product;
    }

    public String removeProductFromCart(int id, int userId) {
        CartContent cartContent = cartContentRepository.findByProductIdAndUserId(id, userId).orElseThrow(() -> new BadRequestException("Cart is empty."));
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
        if (product.getQuantity() < 1) {
            throw new BadRequestException("We don't have that much quantity of that kind.");
        }
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

    @Transactional
    public String removeAllProductsFromCart(int id) {
        cartContentRepository.deleteAllByUserId(id);
        return "Cart is emptied.";
    }
}
