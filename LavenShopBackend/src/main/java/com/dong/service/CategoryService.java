package com.dong.service;


import com.dong.dto.model.CategoryDto;
import com.dong.dto.model.CreateCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryDto categoryDto);
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    CategoryDto updateCategory(CreateCategoryDto categoryDto, Long id);
    void deleteCategory(Long id);
}
