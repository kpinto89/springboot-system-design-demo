package com.demo.systemdesign.service;

import com.demo.systemdesign.async.OrderProcessor;
import com.demo.systemdesign.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderProcessor processor;

    @InjectMocks
    private OrderService orderService;

    @Test
    void placeOrderDelegatesToAsyncProcessor() {
        Order order = new Order();
        order.setId("ord-1");
        order.setProduct("Keyboard");
        order.setQuantity(2);

        orderService.placeOrder(order);

        verify(processor).processAsync(order);
    }
}

