package com.dong.dto.mapper;

import com.dong.dto.model.CategoryDto;
import com.dong.dto.response.CategoryResponseDto;
import com.dong.entity.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {
    private ModelMapper mapper;
    public CategoryDto mapToDto(Category category){
        CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
        return categoryDto;
    }

    public Category mapToEntity(CategoryDto categoryDto){
        Category category = mapper.map(categoryDto, Category.class);
        return category;
    }
    public CategoryResponseDto mapToResponseDto(Category category){
        CategoryResponseDto categoryResponseDto = mapper.map(category, CategoryResponseDto.class);
        return categoryResponseDto;
    }

    public Category mapToResponseEntity(CategoryResponseDto categoryResponseDto){
        Category category = mapper.map(categoryResponseDto, Category.class);
        return category;
    }
}
