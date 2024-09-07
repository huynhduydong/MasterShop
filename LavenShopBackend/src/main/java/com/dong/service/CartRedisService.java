package com.dong.service;

import com.dong.dto.model.ProductDto;
import com.dong.dto.request.CartItemRequest;
import com.dong.dto.request.ProductCartDeletionRequest;
import com.dong.dto.request.UpdateCartRequest;

import java.util.List;
import java.util.Map;


public interface CartRedisService extends BaseRedisService {
    void addProductToCart(String userId, CartItemRequest item);
    void updateProductInCart(String userId, UpdateCartRequest items);
    void deleteProductInCart(String userId, ProductCartDeletionRequest request);
    List<ProductDto> getProductsFromCart(String userId);
}
