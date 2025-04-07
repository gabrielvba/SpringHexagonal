package com.github.gabrielvba.ms_order_management_react.application.port.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management_react.application.port.out.OrderRepository;
import com.github.gabrielvba.ms_order_management_react.domain.model.Order;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Component
@Log4j2
@NoArgsConstructor
public class ConsultOrderUseCase {
	@Autowired
	OrderRepository orderRepository;

	public Flux<Order> execute() {
		log.info("Find all Orders");
		return orderRepository.getAllOrders();
	}
}
