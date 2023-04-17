package com.example.emag.model.DTOs.category;

import com.example.emag.model.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryViewDTO {
    private int id;
    private String name;
    private CategoryViewAddedDTO parentCategory;
}
