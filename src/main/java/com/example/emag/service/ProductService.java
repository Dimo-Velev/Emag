package com.example.emag.service;

import com.example.emag.model.DTOs.product.ProductAddDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.Category;
import com.example.emag.model.entities.User;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService extends AbstractService{

    @Autowired
    private ProductRepository productRepository;

    public ProductViewDTO viewProductById(int id) {
        Product p = getProductById(id);
        return mapper.map(p, ProductViewDTO.class);
    }
    public ProductViewDTO addProduct(ProductAddDTO p) {
        Category c = getCategoryById(p.getCategoryId());
        Product product = mapper.map(p,Product.class);
        product.setCategory(c);
        productRepository.save(product);
        return mapper.map(product, ProductViewDTO.class);
    }

    public ProductViewDTO deleteProductById(int id) { //TODO check if has to be deleted from all tables ?
        Product p = getProductById(id);
        ProductViewDTO respDto = mapper.map(p, ProductViewDTO.class);
        productRepository.deleteById(p.getId());
        return respDto;
    }

    public ProductViewDTO changeProductQty(int id,int qty) {
        Product p = getProductById(id);
        p.setQuantity(qty);
        productRepository.save(p);
        return mapper.map(p,ProductViewDTO.class);
    }

    public List<ProductViewDTO> searchByName(String name) {
        List<Product> products = productRepository.getProductsByNameContaining(name);
        if(products.isEmpty()){
            throw new UnauthorizedException("No products were found with such name");
        }
        return products
                .stream()
                .map(product -> mapper.map(product,ProductViewDTO.class))
                .collect(Collectors.toList());
    }

    public List<ProductViewDTO> viewAllProductsByCategoryId(int id) {
        List<Product> products = productRepository.findAllByCategoryId(id);
        if(products.isEmpty()){
            throw new NotFoundException("No products were found in this category");
        }
        return products.stream()
                .map(product -> mapper.map(product,ProductViewDTO.class))
                .collect(Collectors.toList());
    }


    public ProductViewDTO userViewProductById(int id, int loggedId) {
        Product p = getProductById(id);
        User u = getUserById(loggedId);
        u.getViewedProducts().add(p);
        userRepository.save(u);
        return mapper.map(p, ProductViewDTO.class);
    }
}
