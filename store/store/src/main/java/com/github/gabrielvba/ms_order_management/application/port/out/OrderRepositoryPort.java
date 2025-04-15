package com.github.gabrielvba.ms_order_management.application.port.out;

import java.util.List;

import com.github.gabrielvba.ms_order_management.domain.model.Order;

public interface OrderRepositoryPort {

	Order getOrder(long id);
	List<Order> getAllOrders();
	Order saveOrder(Order orderModel);
	Order updateOrder(Long id, Order orderModel);	
	void deleteOrder(Long id);	

}
