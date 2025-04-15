package com.github.gabrielvba.ms_order_management.factory;

import java.util.List;

import com.github.gabrielvba.ms_order_management.domain.dto.ProductAvailability;
import com.github.gabrielvba.ms_order_management.domain.dto.StockAvailability;

public class StockAvailabilityFactory {

	public static StockAvailability createStockAvailability() {
		return new StockAvailability(1l,true,List.of(new ProductAvailability(1l,true,1,"","")));
	}
	
	public static StockAvailability createStockAvailabilityFalse() {
		return new StockAvailability(1l,false,List.of(new ProductAvailability(1l,false,1,"","")));
	}
}
