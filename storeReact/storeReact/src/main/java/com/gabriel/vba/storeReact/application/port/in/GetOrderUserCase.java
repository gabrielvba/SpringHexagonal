package com.gabriel.vba.storeReact.application.port.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.vba.storeReact.application.port.out.BDRepository;
import com.gabriel.vba.storeReact.domain.model.Order;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Component
@Log4j2
@NoArgsConstructor
public class GetOrderUserCase {
	@Autowired
	BDRepository orderRepository;

	public Flux<Order> execute() {
		log.info("Find all Orders");
		return orderRepository.getAllOrders();
	}
}
