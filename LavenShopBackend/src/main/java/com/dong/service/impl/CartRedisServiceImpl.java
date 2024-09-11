package com.dong.service.impl;


import com.dong.dto.model.ProductInCartDto;
import com.dong.dto.request.CartItemRequest;
import com.dong.dto.request.ProductCartDeletionRequest;
import com.dong.dto.request.UpdateCartRequest;
import com.dong.exception.ResourceNotFoundException;
import com.dong.service.CartRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class CartRedisServiceImpl extends BaseRedisServiceImpl implements CartRedisService {
    private final WebClient webClient;

    @Autowired
    public CartRedisServiceImpl(RedisTemplate<String, Object> redisTemplate, WebClient webClient){
        super(redisTemplate);
        this.webClient = webClient;
    }

    @Override
    public void addProductToCart(String userId, CartItemRequest item) {
        String key = "cart:user-" + userId;
        String fieldKey;
        int updateQuantity;

        if(Objects.nonNull(item.getProductItemId())){
            fieldKey = "product_item:" + item.getProductItemId();
        } else {
            fieldKey = "product:" + item.getProductId();
        }

        if (this.hashExist(userId, fieldKey)) {
            updateQuantity = (Integer) this.hashGet(userId, fieldKey) + item.getQuantity();
        } else {
            updateQuantity = item.getQuantity();
        }

        this.hashSet(key, fieldKey, updateQuantity);
    }

    @Override
    public void updateProductInCart(String userId, UpdateCartRequest item) {
        String key = "cart:user-" + userId;
        String fieldKey;
        long delta = item.getDelta();
        fieldKey = Objects.nonNull(item.getProductItemId()) ?
                "product_item:" + item.getProductItemId() : "product:" + item.getProductId();

        this.hashSet(key, fieldKey, delta);
    }

    @Override
    public void deleteProductInCart(String userId, ProductCartDeletionRequest request) {
        if(Objects.nonNull(request.getProductItemId())){
            this.checkFieldKeyExist("cart:user-" + userId, "product_item:" + request.getProductItemId());
            this.delete("cart:user-" + userId, "product_item:" + request.getProductItemId());
        } else {
            this.checkFieldKeyExist("cart:user-" + userId, "product:" + request.getProductId());
            this.delete("cart:user-" + userId, "product:" + request.getProductId());

        }
    }

    @Override
    public List<ProductInCartDto> getProductsFromCart(String userId) {
        String key = "cart:user-" + userId;
        Map<String, Object> products = this.getField(key);
        List<ProductInCartDto> productList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : products.entrySet()) {
            // Tao 1 bien co hieu de danh dau xem id nay la cua product hay product_item
            boolean isProductItem;

            //Dau tien la lay key ra roi sau do split bang dau ":"
            //vi dinh dang du lieu se la product:1 hoac product_item:1 (dua tren san pham do co option hay ko)
            String[] arrKey = entry.getKey().split(":");

            //Neu la product thi bien co hieu la false, nguoc lai la true
            isProductItem = arrKey[0].equals("product_item");

            ProductInCartDto productDto = getProductById(arrKey[1], isProductItem); // Gọi đến URL để lấy thông tin sản phẩm dua tren id
            if (productDto != null) {
                int quantity = (int)this.hashGet(key, entry.getKey());
                productDto.setQuantity(quantity);
                productList.add(productDto);
            }
        }
        return productList;
    }

    private ProductInCartDto getProductById(String id, boolean isProductItem) {
        // Kiem tra, neu la product_item thi goi toi duong dan lay product dua tren optionId,
        // nguoc lai se la goi product theo id nhu bth
        ProductInCartDto productDto;
        if(isProductItem){
            productDto = this.webClient.get()
                    .uri("http://localhost:8080/api/v1/products/product-options/" + id)
                    .retrieve()
                    .bodyToMono(ProductInCartDto.class)
                    .block();
        } else {
            productDto = this.webClient.get()
                    .uri("http://localhost:8080/api/v1/products/" + id)
                    .retrieve()
                    .bodyToMono(ProductInCartDto.class)
                    .block();
        }

        return productDto;
    }

    private void checkFieldKeyExist(String key, String keyField){
        if(!this.hashExist(key, keyField)){
            throw new ResourceNotFoundException(key, keyField, 0);
        }
    }
}