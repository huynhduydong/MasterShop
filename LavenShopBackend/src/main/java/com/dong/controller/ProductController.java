package com.dong.controller;


import com.dong.dto.model.ProductDto;

import com.dong.dto.response.ObjectResponse;
import com.dong.service.ProductService;
import com.dong.utils.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        return new ResponseEntity<>(this.productService.createProduct(productDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ObjectResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(this.productService.getAllProducts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable(name = "productId") Long id){
        return new ResponseEntity<>(this.productService.getProductById(id), HttpStatus.OK);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestBody ProductDto productDto,
            @PathVariable(name = "productId") Long productId
    ){
        return new ResponseEntity<>(this.productService.updateProduct(productDto, productId), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "productId") Long productId){
        this.productService.deleteProduct(productId);
        return new ResponseEntity<>("Product is deleted successfully!", HttpStatus.OK);
    }
}

