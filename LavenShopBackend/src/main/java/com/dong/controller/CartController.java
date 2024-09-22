package com.dong.controller;


import com.dong.dto.model.ProductInCartDto;
import com.dong.dto.request.CartItemRequest;
import com.dong.dto.request.ProductCartDeletionRequest;
import com.dong.dto.request.UpdateCartRequest;
import com.dong.service.CartRedisService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/carts")
public class CartController {
    private  CartRedisService cartRedisService;

    @GetMapping
    public ResponseEntity<List<ProductInCartDto>> getProductsFromCart(@AuthenticationPrincipal Jwt principal) {
        String userId = principal.getClaimAsString("id");
        return new ResponseEntity<>(this.cartRedisService.getProductsFromCart(userId), HttpStatus.OK);
    }

    @GetMapping("/order-cart")
    public ResponseEntity<List<ProductInCartDto>> getProductsFromCartForOrder(@RequestParam long userId) {
        String userIdString = String.valueOf(userId);
        return new ResponseEntity<>(this.cartRedisService.getProductsFromCart(userIdString), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addProductToCart(
            @AuthenticationPrincipal Jwt principal,
            @RequestBody CartItemRequest item
    ){
        String userId = principal.getClaimAsString("id");

        this.cartRedisService.addProductToCart(userId, item);
        return new ResponseEntity<>("Add to cart successfully!", HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<String> updateProductInCart(
            @AuthenticationPrincipal Jwt principal,
            @RequestBody UpdateCartRequest item
    ){
        String userId = principal.getClaimAsString("id");

        this.cartRedisService.updateProductInCart(userId, item);

        return new ResponseEntity<>("Update cart successfully!", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductInCart(
             @AuthenticationPrincipal Jwt principal,
            @RequestBody ProductCartDeletionRequest item
    ){
        String userId = principal.getClaimAsString("id");
        this.cartRedisService.deleteProductInCart(userId, item);
        return new ResponseEntity<>("Product delete from cart successfully!", HttpStatus.OK);
    }
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllProductsInCart(
            @AuthenticationPrincipal Jwt principal
    ){
        String userId = principal.getClaimAsString("id");
        this.cartRedisService.deleteAllProductsInCart(userId);
        return new ResponseEntity<>("Delete all products in cart successfully!", HttpStatus.OK);
    }
}