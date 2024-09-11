package com.dong.service;

import com.dong.dto.model.OrderDto;
import com.dong.dto.response.ListOrderDto;

public interface OrderService {
    OrderDto createOrder(String userId);
    ListOrderDto getAllOrder(int pageNo, int pageSize, String sortBy, String sortDir);
    OrderDto getOrderById(long id);
    OrderDto updateOrder(OrderDto orderDto, long id);
    String cancelOrderById(long id);
    String checkoutOrderById(long id);


}
