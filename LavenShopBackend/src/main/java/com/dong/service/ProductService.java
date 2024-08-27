package com.dong.service;

import com.dong.dto.model.ProductDto;
import com.dong.dto.response.ProductResponse;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);
    ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);

    ProductDto getProductById(Long id);
//    ProductDto getProductById(Long id);
//    ProductDto updateProduct(ProductDto productDto, Long productId);
//    void deleteProduct(Long productId);
}
