package com.example.emag.service;

import com.example.emag.model.DTOs.product.ProductAddDTO;
import com.example.emag.model.DTOs.product.ProductEditDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.Category;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

@Service
public class ProductService extends AbstractService {

    @Autowired
    private ProductRepository productRepository;

    public ProductViewDTO viewProductById(int id) {
        Product p = getProductById(id);
        return mapper.map(p, ProductViewDTO.class);
    }

    public ProductViewDTO addProduct(ProductAddDTO p) {
        Category c = getCategoryById(p.getCategoryId());
        Product product = mapper.map(p, Product.class);
        product.setCategory(c);
        productRepository.save(product);
        return mapper.map(product, ProductViewDTO.class);
    }

    @Transactional
    public ProductViewDTO deleteProductById(int id) {
        Product p = getProductById(id);
        ProductViewDTO respDto = mapper.map(p, ProductViewDTO.class);
        productRepository.deleteById(p.getId());
        return respDto;
    }

    public ProductViewDTO changeProductQty(int id, int qty) {
        Product p = getProductById(id);
        p.setQuantity(qty);
        productRepository.save(p);
        return mapper.map(p, ProductViewDTO.class);
    }

    public Page<ProductViewDTO> searchByName(String name, Pageable pageable) {
        Page<Product> products = productRepository.getProductsByNameStartsWith(name, pageable);
        if (products.isEmpty()) {
            throw new UnauthorizedException("No products were found with such name");
        }
        return products.map(product -> mapper.map(product, ProductViewDTO.class));
    }

    public Page<ProductViewDTO> viewAllProductsByCategoryId(Pageable pageable, int id) {
        Page<Product> productPage = productRepository.findAllByCategoryId(id, pageable);
        if (productPage.isEmpty()) {
            throw new NotFoundException("No products were found in this category");
        }
        return productPage.map(product -> mapper.map(product, ProductViewDTO.class));
    }

    public ProductViewDTO userViewProductById(int id, int loggedId) {
        Product p = getProductById(id);
        User u = getUserById(loggedId);
        u.getViewedProducts().add(p);
        userRepository.save(u);
        return mapper.map(p, ProductViewDTO.class);
    }

    public ProductViewDTO editProductDetails(int id, ProductEditDTO dto) {
        Product p = getProductById(id);
        p.setName(dto.getName());
        if (dto.getDescription() != null) {
            p.setDescription(dto.getDescription());
        }
        p.setPrice(dto.getPrice());
        p.setCategory(getCategoryById(dto.getCategoryId()));
        productRepository.save(p);
        return mapper.map(p, ProductViewDTO.class);
    }
}
