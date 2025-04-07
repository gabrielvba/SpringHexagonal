package com.github.gabrielvba.ms_order_management.domain.dto;

import java.util.List;

public record StockAvailability(
	    Long orderId,
	    boolean available,
	    List<ProductAvailability> itens
	) {}