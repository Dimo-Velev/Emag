package com.example.emag.service;

import com.example.emag.model.DTOs.category.CategoryAddDTO;
import com.example.emag.model.DTOs.category.CategoryViewAddedDTO;
import com.example.emag.model.DTOs.category.CategoryViewDTO;
import com.example.emag.model.entities.Category;
import com.example.emag.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService {

    public CategoryViewAddedDTO addCategory(CategoryAddDTO dto) {
        if(categoryRepository.existsByNameIgnoreCase(dto.getName())){
            throw new BadRequestException("Category with such name already exists!");
        }
        Category c = new Category();
        if(dto.getParentCategoryId() != 0){
            Category parent = getCategoryById(dto.getParentCategoryId());
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

    public CategoryViewDTO deleteCategoryById(int id) { //TODO update the child categories of the deleted category to set their parentCategory field to null
        Category c = getCategoryById(id);
        CategoryViewDTO respDto = mapper.map(c, CategoryViewDTO.class);
        categoryRepository.deleteById(c.getId());
        return respDto;
    }



}
