package com.example.spring.service;

import com.example.spring.domain.Order;
import com.example.spring.result.GetOrderResult;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public GetOrderResult find(String orderId, String caller) {
        Order order = new Order("first", 123);
        return new GetOrderResult.Found(order);
    }
}
