package com.example.emag.service;

import com.example.emag.model.DTOs.category.CategoryAddDTO;
import com.example.emag.model.DTOs.category.CategoryViewAddedDTO;
import com.example.emag.model.DTOs.category.CategoryViewDTO;
import com.example.emag.model.entities.Category;
import com.example.emag.model.exceptions.BadRequestException;
import com.example.emag.model.exceptions.NotFoundException;
import com.example.emag.model.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService {
    @Autowired
    protected CategoryRepository categoryRepository;

    public CategoryViewAddedDTO addCategory(CategoryAddDTO dto) {
        if(categoryRepository.existsByNameIgnoreCase(dto.getName())){
            throw new BadRequestException("Category with such name already exists!");
        }
        Category c = new Category();
        if(dto.getParentCategoryId() != 0){
            Category parent = categoryRepository
                    .findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new NotFoundException("Parent category not found"));
            c.setParentCategory(parent);
        }
        c.setName(dto.getName());
        categoryRepository.save(c);
        return mapper.map(c, CategoryViewAddedDTO.class);
    }


    public CategoryViewDTO viewCategoryById(int id) {
        Category p = getCategoryById(id);
        return mapper.map(p, CategoryViewDTO.class);
    }

    @Transactional
    public CategoryViewDTO deleteCategoryById(int id) {
        Category c = getCategoryById(id);
        CategoryViewDTO respDto = mapper.map(c, CategoryViewDTO.class);
        categoryRepository.deleteById(c.getId());
        return respDto;
    }

}
