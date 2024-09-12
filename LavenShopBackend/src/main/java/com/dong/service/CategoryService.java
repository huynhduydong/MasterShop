package com.dong.service;


import com.dong.dto.model.CategoryDto;
import com.dong.dto.model.CreateCategoryDto;
import com.dong.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CreateCategoryDto categoryDto);
    CategoryDto getCategoryById(Long id);
    List<CategoryResponseDto> getAllCategories();
    CategoryDto updateCategory(CreateCategoryDto categoryDto, Long id);
    void deleteCategory(Long id);
}
