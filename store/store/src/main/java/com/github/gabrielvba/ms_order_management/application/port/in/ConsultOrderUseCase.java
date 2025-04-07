package com.github.gabrielvba.ms_order_management.application.port.in;


import java.util.List;

import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.rules.ChangeStatus;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ConsultOrderUseCase {
	private final OrderRepositoryPort orderRepository;
	
	private final ChangeStatus changeStatus;

	public ConsultOrderUseCase(OrderRepositoryPort orderRepository, ChangeStatus changeStatus) {
		this.orderRepository = orderRepository;
		this.changeStatus = changeStatus;

	}
	
	public List<Order> execute() {
		log.info("Find all Orders");
		return orderRepository.getAllOrders();
	}
	
	
}
