package com.gabriel.vba.storeReact.application.port.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.vba.storeReact.application.port.out.BDRepository;
import com.gabriel.vba.storeReact.domain.model.Order;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@NoArgsConstructor
public class UpdateOrderUserCase {

	@Autowired
	BDRepository orderRepository;
	
	private Mono<Void> execute(Long id, Order orderModel) {
        log.info("Update Order with ID: {}", id);
        return orderRepository.updateOrder(id, orderModel)
            .doOnSuccess(saved -> log.info("SUCCESS! Order has been Update with ID: {}", orderModel.getId()))
            .then(); // Converte Mono<Order> para Mono<Void>
    }

}
