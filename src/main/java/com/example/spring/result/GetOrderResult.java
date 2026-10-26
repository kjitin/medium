package com.example.spring.result;

import com.example.spring.domain.Order;

public sealed interface GetOrderResult {

    record Found(Order order) implements GetOrderResult {}
    record NotFound(String orderId) implements GetOrderResult {}
    record Forbidden(String orderId, String reason) implements GetOrderResult {}
}
