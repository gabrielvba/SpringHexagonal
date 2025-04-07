package com.github.gabrielvba.ms_order_management.port.out;

import java.util.List;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.domain.model.Order;

public class OrderRepositoryMock implements OrderRepositoryPort{

	@Override
	public Order getOrders(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order saveOrder(Order orderModel) {
		return orderModel;
	}

	@Override
	public Order updateOrder(Long id, Order orderModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrder(Long id) {
		// TODO Auto-generated method stub
		
	}

}
