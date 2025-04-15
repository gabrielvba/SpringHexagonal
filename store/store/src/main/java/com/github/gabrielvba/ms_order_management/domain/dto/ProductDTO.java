package com.github.gabrielvba.ms_order_management.domain.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductDTO(Long productId, BigDecimal unitPrice, String name, String description) { }
