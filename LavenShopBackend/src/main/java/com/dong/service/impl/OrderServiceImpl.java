package com.dong.service.impl;

import com.dong.dto.model.OrderDto;
import com.dong.dto.model.ProductWithAddressDto;
import com.dong.dto.response.ListOrderDto;
import com.dong.repositories.OrderRepository;
import com.dong.service.OrderService;
import com.dong.utils.CustomHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private WebClient webClient;

    @Override
    public OrderDto createOrder(String userId) {
        List<ProductWithAddressDto> listProductInCart = webClient.get()
                .uri("https://localhost:8080/api/v1/carts")
                .header(CustomHeaders.X_AUTH_USER_ID, userId).retrieve()
                .bodyToFlux(ProductWithAddressDto.class)
                .collectList().block();

        return null;
    }

    @Override
    public ListOrderDto getAllOrder(int pageNo, int pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public OrderDto getOrderById(long id) {
        return null;
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, long id) {
        return null;
    }

    @Override
    public String cancelOrderById(long id) {
        return null;
    }

    @Override
    public String checkoutOrderById(long id) {
        return null;
    }
}
