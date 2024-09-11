package com.dong.dto.model;

import lombok.Data;
@Data
public class ProductInCartDto {
    private Long id;
    private String name;
    private double price;
    private double discountRate;
    private String thumbnailUrl;
    private ProductOptionDto option;
}