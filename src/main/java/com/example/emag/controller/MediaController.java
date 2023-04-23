package com.example.emag.controller;

import com.example.emag.model.DTOs.ProductImageDTO;
import com.example.emag.model.exceptions.BadRequestException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
public class MediaController extends AbstractController{

    @PostMapping("/media/products/{id}")
    public ProductImageDTO uploadProductImage(@RequestParam("file") MultipartFile file, @PathVariable int id){
        return mediaService.uploadProductImage(file, id);
    }

    @SneakyThrows
    @GetMapping("/media/products/{filename}")
    public void downloadProductImage(@PathVariable("filename") String fileName, HttpServletResponse resp){
        File f = mediaService.downloadProductImage(fileName);
        //TODO
        // ne e zaduljitelno dolnoto!
        // resp.setContentType("image/jpeg");
        Files.copy(f.toPath(), resp.getOutputStream());
    }
    @PostMapping("/media/reviews/{id:\\d+}")
    public ResponseEntity<String> uploadPictureToReview(@RequestParam("file") MultipartFile file, @PathVariable int id, HttpSession session){
        checkIfValidPicture(file);
        mediaService.uploadReviewPicture(file,id,getLoggedId(session));
        return ResponseEntity.ok("Review image uploaded to Review.");
    }

    private void checkIfValidPicture(MultipartFile file){
        if (file.isEmpty()) {
           throw new BadRequestException("File is empty");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new BadRequestException("File size exceeds 5 MB");
        }
        if (!file.getContentType().startsWith("image/")) {
            throw new BadRequestException("Only images are allowed");
        }
    }
}
