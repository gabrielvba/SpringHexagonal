package com.github.gabrielvba.ms_order_management.domain.dto;

public record ProductAvailability(
	    Long productId,
	    boolean available,
	    int quantityAvailable,
	    String warehouseId,
	    String expectedRestockDate
	) {}