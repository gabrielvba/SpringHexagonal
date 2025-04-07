package com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public record Order(Long orderId,
				    Long customerId,
				    List<ProductItem> items,
				    BigDecimal totalAmount,
				    String status,
				    String deliveryAddress,
				    Payment payment,
				    String promotionCode,
				    LocalDateTime estimatedDeliveryDate,
				    LocalDateTime completionDate,
				    Long shipmentId) {
}