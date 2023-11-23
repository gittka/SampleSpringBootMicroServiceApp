package com.alxtek.orderservice.controller;

import com.alxtek.orderservice.dto.OrderDtoRequest;
import com.alxtek.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderDtoRequest orderDtoRequest){
        orderService.createOrder(orderDtoRequest);
        return "Order created successfully";
    }
}
