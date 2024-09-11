package com.dong.service;

import com.dong.dto.model.ProductDto;
import com.dong.dto.response.ObjectResponse;
import com.dong.dto.response.ProductResponse;
import com.dong.dto.response.ProductWithOptionDto;
import com.dong.dto.response.ProductWithOptionForCartDto;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ObjectResponse<ProductDto> getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductDto getProductById(Long id);
    ProductDto updateProduct(ProductDto productDto, Long productId);
    void deleteProduct(Long productId);

    ProductWithOptionForCartDto getProductByProductOptionId(String productOptionId);
}
