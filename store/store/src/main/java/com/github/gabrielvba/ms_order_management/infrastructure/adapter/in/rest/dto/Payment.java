package com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Payment(  Long paymentId,
					    Long orderId,
					    Long external_transaction_id,
					    String paymentMethod,
					    String status,
					    LocalDateTime completionDate,
					    BigDecimal totalAmount) {
}