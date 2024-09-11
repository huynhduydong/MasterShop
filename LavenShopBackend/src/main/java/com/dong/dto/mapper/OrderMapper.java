package com.dong.dto.mapper;

import com.dong.dto.model.OrderDto;
import com.dong.entity.Order;
import org.modelmapper.ModelMapper;

public class OrderMapper {

    private ModelMapper mapper;

    private OrderDto mapToDto(Order order){
        OrderDto orderDto = mapper.map(order, OrderDto.class);
        return orderDto;
    }

    private Order mapToEntity(OrderDto orderDto){
        Order order = mapper.map(orderDto, Order.class);
        return order;
    }
}