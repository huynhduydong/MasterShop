package com.dong.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "shop_order")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ShopOrder extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private CustomerPaymentMethod paymentMethod;

    @Column(nullable = false, length = 256)
    private String shippingAddress;

    @ManyToOne
    @JoinColumn(name = "shipping_method_id", nullable = false)
    private ShippingMethod shippingMethod;

    @Column(nullable = false)
    private double orderTotal;

    @ManyToOne
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus orderStatus;
}
