package com.dong.service.impl;



import com.dong.repositories.CartItemRepository;
import com.dong.repositories.CartRepository;
import com.dong.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    @Override
    public void addToCart(long userId, long productId, long quantity){

    }
}
