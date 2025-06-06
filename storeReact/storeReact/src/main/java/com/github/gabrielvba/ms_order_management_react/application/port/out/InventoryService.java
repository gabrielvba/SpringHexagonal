package com.github.gabrielvba.ms_order_management_react.application.port.out;

import com.github.gabrielvba.ms_order_management_react.domain.model.Order;

import reactor.core.publisher.Mono;

public interface InventoryService {
	Mono validateInventory(Order orderModel);
}
