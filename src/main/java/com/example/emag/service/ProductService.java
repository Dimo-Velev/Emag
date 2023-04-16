package com.example.emag.service;

import com.example.emag.model.DTOs.product.ProductAddDTO;
import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.entities.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractService{

    public ProductViewDTO viewProductById(int id) {
        Product p = getProductById(id);
        return mapper.map(p, ProductViewDTO.class);
    }
    public ProductViewDTO addProduct(ProductAddDTO p) {
        Product product = mapper.map(p,Product.class);
        productRepository.save(product);
        return mapper.map(product, ProductViewDTO.class);
    }

    public ProductViewDTO deleteProductById(int id) {
        Product p = getProductById(id);
        ProductViewDTO respDto = mapper.map(p, ProductViewDTO.class); //TODO change to fullInfoForDeletedProductDTO, tbc
        productRepository.deleteById(p.getId());
        return respDto;
    }




}
