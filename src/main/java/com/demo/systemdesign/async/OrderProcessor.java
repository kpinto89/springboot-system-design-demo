package com.demo.systemdesign.async;

import com.demo.systemdesign.model.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor {

    @Async
    public void processAsync(Order order) {
        try {
            Thread.sleep(2000); // simulate DB / external call
        } catch (InterruptedException ignored) {}

        System.out.println("Order processed: " + order.getId());
    }
}
