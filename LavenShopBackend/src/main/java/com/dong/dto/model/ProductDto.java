package com.dong.dto.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class ProductDto {

    private Long id;
    @NotEmpty(message = "Tên không được bỏ trống")
    @Size(min = 2, message = "Tên phải có độ dài tối thiểu là 2 ký tự")
    private String name;
    @NotEmpty(message = "Thương hiệu không được bỏ trống")
    @Size(min = 2, message = "Thương hiệu sản phẩm phải có ít nhất 2 ký tự!")
    private String brand;
    @NotEmpty(message = "Mô tả không được bỏ trống")
    @Size(min = 10, message = "Mô tả sản phẩm phải có ít nhất 2 ký tự!")
    private String description;
    @NotEmpty(message = "Giá không được bỏ trống")
    @Positive(message = "Giá phải lớn hơn 0")
    private double price;
    private double discountRate;
    @NotEmpty(message = "thumbnail không được bỏ trống")
    private String thumbnailUrl;
    private int reviewCount;
    private double ratingAverage;
    private int quantitySold;
    private String productSlug;
    private String categoryUrl;

    private Set<ProductOptionDto> options;
    private Set<ProductSpecificationDto> specifications;
}
