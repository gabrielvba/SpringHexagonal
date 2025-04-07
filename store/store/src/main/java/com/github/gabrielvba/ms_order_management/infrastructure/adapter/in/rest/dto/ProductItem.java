package com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public record ProductItem(	Long id,
						    Long externalProductId,
						    String name,
						    BigDecimal unitPrice,
						    int quantity,
						    String promotionCode) {
}