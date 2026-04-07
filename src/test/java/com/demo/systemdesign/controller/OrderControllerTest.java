package com.demo.systemdesign.controller;

import com.demo.systemdesign.limiter.RateLimiter;
import com.demo.systemdesign.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private RateLimiter rateLimiter;

    @Test
    void createReturnsOkWhenRateLimiterAllowsRequest() throws Exception {
        when(rateLimiter.allow("client-1")).thenReturn(true);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "ord-1",
                                  "product": "Keyboard",
                                  "quantity": 2
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Order accepted"));

        verify(orderService).placeOrder(any());
    }

    @Test
    void createReturnsTooManyRequestsWhenRateLimiterRejectsRequest() throws Exception {
        when(rateLimiter.allow("client-1")).thenReturn(false);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": "ord-2",
                                  "product": "Mouse",
                                  "quantity": 1
                                }
                                """))
                .andExpect(status().isTooManyRequests())
                .andExpect(content().string("Too many requests"));

        verify(orderService, never()).placeOrder(any());
    }
}

