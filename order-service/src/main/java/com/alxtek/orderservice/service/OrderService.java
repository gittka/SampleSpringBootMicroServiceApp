package com.alxtek.orderservice.service;

import com.alxtek.orderservice.dto.InventoryResponse;
import com.alxtek.orderservice.dto.OrderDtoRequest;
import com.alxtek.orderservice.dto.OrderLineItemsDtoRequest;
import com.alxtek.orderservice.model.Order;
import com.alxtek.orderservice.model.OrderLineItems;
import com.alxtek.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder getWebClientBuilder;

    public void createOrder(OrderDtoRequest orderDtoRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderDtoRequest.getOrderLineItemsDtoRequests()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] result = getWebClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder-> uriBuilder.queryParam("skuCode", skuCodes)
                        .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInSstock = Arrays.stream(result)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInSstock){
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order failed");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDtoRequest orderDtoRequest1) {
        return OrderLineItems
                .builder()
                .price(orderDtoRequest1.getPrice())
                .skuCode(orderDtoRequest1.getSkuCode())
                .quantity(orderDtoRequest1.getQuantity())
                .build();
    }
}