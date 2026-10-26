package com.example.spring.controller;

import com.example.spring.result.GetOrderResult;
import com.example.spring.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orders;

    public OrderController(OrderService orders) {
        this.orders = orders;
    }

    ResponseEntity<?> getOrders(@PathVariable String orderId, Principal caller) {
        return switch (orders.find(orderId, caller.getName())) {
            case GetOrderResult.Found f ->  ResponseEntity.ok(f.order());
            case GetOrderResult.Forbidden f ->
                problem(HttpStatus.FORBIDDEN, "Access Denied", f.reason());
            case GetOrderResult.NotFound n ->
                problem(HttpStatus.NOT_FOUND, "Not Found", n.toString());

        };
    }

    private ResponseEntity<?> problem(HttpStatus httpStatus, String title, String detail) {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(httpStatus, detail);
        body.setTitle(title);
        return ResponseEntity.status(httpStatus).body(body);
    }
}
