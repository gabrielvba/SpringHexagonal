package com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.gateway;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management.application.port.out.StockServicePort;
import com.github.gabrielvba.ms_order_management.domain.dto.ProductAvailability;
import com.github.gabrielvba.ms_order_management.domain.dto.StockAvailability;
import com.github.gabrielvba.ms_order_management.domain.model.Order;

@Component
public class StockGateway implements StockServicePort{

	@Override
	public Order validateInventory(Order orderModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockAvailability getOrderAvailibility(Order itens) {
		// TODO Auto-generated method stub
		return new StockAvailability(null, true, List.of(new ProductAvailability(null, true, 0, null, null)));
	}

	@Override
	public void createStockOrder(Order orderModel) {
		// TODO Auto-generated method stub
		
	}

}
