package com.github.gabrielvba.ms_order_management.domain.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public record ProductDTO(Long productId, BigDecimal unitPrice, String name, String description) { }
