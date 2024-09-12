package com.dong.service;

import com.dong.dto.model.ProductDto;
import com.dong.dto.response.*;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ObjectResponse<ProductDto> getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductDetailResponseDto getProductById(Long id);
    ProductDto updateProduct(ProductDto productDto, Long productId);
    void deleteProduct(Long productId);

    ProductWithOptionForCartDto getProductByProductOptionId(String productOptionId);
}
