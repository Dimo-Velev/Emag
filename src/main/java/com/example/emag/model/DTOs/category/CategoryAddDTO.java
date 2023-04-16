package com.example.emag.model.DTOs.category;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CategoryAddDTO {
    @NotNull
    private String name;
    private int parentCategoryId;

}
