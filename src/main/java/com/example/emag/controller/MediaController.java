package com.example.emag.controller;

import com.example.emag.model.DTOs.ProductImageDTO;
import com.example.emag.service.MediaService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;

@RestController
public class MediaController extends AbstractController{

    @Autowired
    private MediaService mediaService;

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


}
