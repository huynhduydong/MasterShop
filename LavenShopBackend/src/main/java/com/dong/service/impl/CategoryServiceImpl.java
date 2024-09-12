package com.dong.service.impl;


import com.dong.dto.mapper.CategoryMapper;
import com.dong.dto.mapper.CreateCategoryMapper;
import com.dong.dto.model.CategoryDto;
import com.dong.dto.model.CreateCategoryDto;
import com.dong.dto.response.CategoryResponseDto;
import com.dong.entity.Category;
import com.dong.exception.ResourceNotFoundException;
import com.dong.repositories.CategoryRepository;
import com.dong.service.CategoryService;
import com.dong.utils.SlugConvert;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    private CreateCategoryMapper createCategoryMapper;
    private CategoryMapper categoryMapper;
    @Override
    public CategoryDto createCategory(CreateCategoryDto categoryDto) {
        Category newCategory = this.createCategoryMapper.mapToEntity(categoryDto);

        newCategory.setUrlKey(SlugConvert.convert(categoryDto.getName()));

        Category responseCategory = this.categoryRepository.save(newCategory);

        return this.categoryMapper.mapToDto(responseCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        CategoryDto categoryDto = this.categoryMapper.mapToDto(category);
        return categoryDto;
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();

        return categories.stream().map(category -> this.categoryMapper.mapToResponseDto(category)).collect(Collectors.toList());    }

    @Override
    public CategoryDto updateCategory(CreateCategoryDto categoryDto, Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setName(categoryDto.getName());
        category.setThumbnailUrl(categoryDto.getThumbnailUrl());
//        category.setPrimary(categoryDto.isPrimary());
        category.setParentId(categoryDto.getParentId());
        category.setUrlKey(SlugConvert.convert(categoryDto.getName()));

        Category responseCategory = this.categoryRepository.save(category);

        return this.categoryMapper.mapToDto(responseCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        this.categoryRepository.delete(category);
    }
}
