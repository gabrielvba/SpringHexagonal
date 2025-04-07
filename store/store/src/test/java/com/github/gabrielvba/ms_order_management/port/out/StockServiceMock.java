package com.github.gabrielvba.ms_order_management.port.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.gabrielvba.ms_order_management.application.port.out.StockServicePort;
import com.github.gabrielvba.ms_order_management.domain.dto.ProductAvailability;
import com.github.gabrielvba.ms_order_management.domain.dto.StockAvailability;
import com.github.gabrielvba.ms_order_management.domain.model.Order;

public class StockServiceMock implements StockServicePort {

	Map<Long, ProductAvailability> productsStock = new HashMap<>(Map.of(
		    		1l,new ProductAvailability(1l,true,1,"1a3bd4",null), 
		    		2l,new ProductAvailability(2l,true,1,"1a3bd4",null), 
		    		3l,new ProductAvailability(3l,true,1,"1a3bd4",null), 
		    		4l,new ProductAvailability(4l,true,1,"1a3bd4",null), 
		    		5l,new ProductAvailability(5l,false,0,"1a3bd4",null)
		    )
		);	
	
	@Override
	public Order validateInventory(Order orderModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockAvailability getOrderAvailibility(Order order) {
		List<ProductAvailability> matchedProducts = order.getItems().stream()
				.map(item -> productsStock.get(item.getId()))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		return matchedProducts.size() == order.getItems().size() 
				? new StockAvailability(1l, true, matchedProducts)
				: new StockAvailability(1l, false, matchedProducts);
	}

	@Override
	public void createStockOrder(Order orderModel) {
		// TODO Auto-generated method stub
		
	}

}
