package com.dong.dto.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private long id;
    private double total;
    private String status;
    private String paymentMethod;
    private String paymentStatus;
    private double shippingFee;
    private String note;
    private LocalDateTime createdAt;
    private AddressInOrderDto address;

}
