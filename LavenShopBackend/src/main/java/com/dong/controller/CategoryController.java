package com.dong.controller;


import com.dong.dto.model.CategoryDto;
import com.dong.dto.model.CreateCategoryDto;
import com.dong.dto.response.CategoryResponseDto;
import com.dong.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private CategoryService categoryService;
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryDto createCategoryDto){
        return new ResponseEntity<>(this.categoryService.createCategory(createCategoryDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        return new ResponseEntity<>(this.categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name = "categoryId") Long id){
        return new ResponseEntity<>(this.categoryService.getCategoryById(id), HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable(name = "categoryId") Long id,
            @RequestBody CreateCategoryDto createCategoryDto
    ){
        return new ResponseEntity<>(this.categoryService.updateCategory(createCategoryDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable(name = "categoryId") Long id
    ){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>("Delete category successfully!", HttpStatus.OK);
    }
}