package com.demo.systemdesign.controller;

import com.demo.systemdesign.limiter.RateLimiter;
import com.demo.systemdesign.model.Order;
import com.demo.systemdesign.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final RateLimiter rateLimiter;

    public OrderController(OrderService orderService, RateLimiter rateLimiter) {
        this.orderService = orderService;
        this.rateLimiter = rateLimiter;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Order order) {

        if (!rateLimiter.allow("client-1")) {
            return ResponseEntity.status(429).body("Too many requests");
        }

        orderService.placeOrder(order);
        return ResponseEntity.ok("Order accepted");
    }
}
