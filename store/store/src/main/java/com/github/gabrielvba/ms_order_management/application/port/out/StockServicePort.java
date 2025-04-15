package com.github.gabrielvba.ms_order_management.application.port.out;

import com.github.gabrielvba.ms_order_management.domain.dto.StockAvailability;
import com.github.gabrielvba.ms_order_management.domain.model.Order;

public interface StockServicePort {

	StockAvailability getOrderAvailibility(Order itens);

	void createStockOrder(Order orderModel);
	
	void cancelStockOrder(Order orderModel);

}
