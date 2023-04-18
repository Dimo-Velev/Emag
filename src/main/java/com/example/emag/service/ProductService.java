package com.example.emag.service;

import com.example.emag.model.DTOs.product.ProductAddDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.Category;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public ProductViewDTO deleteProductById(int id) {
        Product p = getProductById(id);
        ProductViewDTO respDto = mapper.map(p, ProductViewDTO.class); //TODO change to fullInfoForDeletedProductDTO, tbc
        productRepository.deleteById(p.getId());
        return respDto;
    }

    public ProductViewDTO changeProductQty(int id,int qty) {
        Product p = getProductById(id);
        p.setQuantity(qty);
        productRepository.save(p);
        return mapper.map(p,ProductViewDTO.class);
    }

    public ProductViewDTO searchByName(String name) {
        Optional<Product> p = productRepository.getProductByNameIgnoreCase(name);
        if(p.isEmpty()){
            throw new UnauthorizedException("No product with such name");
        }
        return mapper.map(p, ProductViewDTO.class);
    }
}
