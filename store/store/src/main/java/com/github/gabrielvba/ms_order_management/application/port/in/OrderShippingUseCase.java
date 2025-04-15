package com.github.gabrielvba.ms_order_management.application.port.in;

import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.application.port.out.StockServicePort;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.OrderStatus;
import com.github.gabrielvba.ms_order_management.domain.rules.ChangeStatus;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class OrderShippingUseCase {
	private final OrderRepositoryPort orderRepository;
	private final ChangeStatus changeStatus;

	public OrderShippingUseCase(OrderRepositoryPort orderRepository, ChangeStatus changeStatus) {
		this.orderRepository = orderRepository;
		this.changeStatus = changeStatus;

	}
	
	public Order execute(Long id, Order orderModel) {
		changeStatus.execute(orderModel, getOrder(id));
		return orderRepository.updateOrder(id, orderModel);
	}
		
	private Order getOrder(Long id) {
		log.info("Get Order with ID: {}", id);
		return orderRepository.getOrder(id);
	}	
}
