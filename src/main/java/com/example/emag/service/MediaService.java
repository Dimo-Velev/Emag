package com.example.emag.service;

import com.example.emag.model.DTOs.ProductImageDTO;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.ProductImage;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class MediaService extends AbstractService{
    public ProductImageDTO uploadProductImage(MultipartFile file, int productId) {
        try{
            Product p = getProductById(productId);
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            String name = UUID.randomUUID() + "."+ext;
            File dir = new File("/uploads");
            if(!dir.exists()){
                dir.mkdirs();
            }
            File f = new File(dir, name);
            Files.copy(file.getInputStream(), f.toPath());
            String url = dir.getName() + File.separator + f.getName();
            ProductImage productImage = new ProductImage();
            productImage.setProduct(p);
            productImage.setPictureUrl(url);
            productImageRepository.save(productImage);
            return mapper.map(productImage, ProductImageDTO.class);
        }
        catch (IOException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public File downloadProductImage(String fileName) {
        File dir = new File("/uploads");
        File f = new File(dir, fileName);
        if(f.exists()){
            return f;
        }
        throw new NotFoundException("File not found");
    }
}
