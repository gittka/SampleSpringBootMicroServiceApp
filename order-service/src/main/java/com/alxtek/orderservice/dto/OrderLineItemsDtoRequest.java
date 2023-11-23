package com.alxtek.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemsDtoRequest {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
