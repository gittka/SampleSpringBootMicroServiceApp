package com.alxtek.orderservice.controller;

import com.alxtek.orderservice.dto.OrderDtoRequest;
import com.alxtek.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    /**
     * Creates an order using the provided order data.
     *
     * @param  orderDtoRequest  the order data used to create the order
     * @return                  a string indicating the success of the order creation
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="inventory", fallbackMethod = "createOrderFallback")
    @TimeLimiter(name="inventory")
    @Retry(name="inventory")
    public CompletableFuture<String>  createOrder(@RequestBody OrderDtoRequest orderDtoRequest){
        return CompletableFuture.supplyAsync(()-> orderService.createOrder(orderDtoRequest)) ;
    }

    /**
     * Creates an order with the given order data. If an exception occurs, a fallback message is returned.
     *
     * @param  orderDtoRequest  the order data
     * @param  e                the exception that occurred
     * @return                  a message indicating that the order failed and to try again later
     */
    public CompletableFuture<String> createOrderFallback(OrderDtoRequest orderDtoRequest, RuntimeException e){
        return CompletableFuture.supplyAsync(() -> "Order failed! Something went wrong, please try again later!");
    }
}
