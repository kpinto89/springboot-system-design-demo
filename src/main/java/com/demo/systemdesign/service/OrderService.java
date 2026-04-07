package com.demo.systemdesign.service;

import com.demo.systemdesign.async.OrderProcessor;
import com.demo.systemdesign.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderProcessor processor;

    public OrderService(OrderProcessor processor) {
        this.processor = processor;
    }

    public void placeOrder(Order order) {
        processor.processAsync(order);
    }
}
