package com.example.emag.service;

import com.example.emag.model.DTOs.ProductImageDTO;
import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.ProductImage;
import com.example.emag.model.entities.Review;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.exceptions.UnauthorizedException;
import com.example.emag.model.repositories.ProductImageRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class MediaService extends AbstractService{
    @Autowired
    protected ProductImageRepository productImageRepository;

    @Transactional
    public ProductImageDTO uploadProductImage(MultipartFile file, int productId) {
        try{
            Product p = getProductById(productId);
            String url = getURL(file);
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

    public File downloadImage(String fileName) {
        File dir = new File("uploads");
        File f = new File(dir, fileName);
        if(f.exists()){
            return f;
        }
        throw new NotFoundException("File not found");
    }

    @Transactional
    public void uploadReviewPicture(MultipartFile file, int id, int userId) {
        try{
            Review review = getReviewById(id);
            if(userId != review.getUser().getId()){
                throw new UnauthorizedException("Access denied");
            }
            String url = getURL(file);
            review.setPictureUrl(url);
            reviewRepository.save(review);
        }
        catch (IOException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private String getURL(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = UUID.randomUUID() + "."+extension;
        File dir = new File("uploads");
        if(!dir.exists()){
            dir.mkdirs();
        }
        File picture = new File(dir, name);
        Files.copy(file.getInputStream(), picture.toPath());
        String url = dir.getName() + File.separator + picture.getName();
        return url;
    }



}
