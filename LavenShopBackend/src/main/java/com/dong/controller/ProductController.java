package com.dong.controller;

import com.dong.dto.model.ProductDto;
import com.dong.dto.response.ProductResponse;
import com.dong.service.ProductService;
import com.dong.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")


public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ProductResponse> getAllProducts(
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

}
