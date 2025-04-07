package com.github.gabrielvba.ms_order_management_react.application.port.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management_react.application.port.out.OrderRepository;
import com.github.gabrielvba.ms_order_management_react.domain.model.Order;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@NoArgsConstructor
public class PaymentProcessUseCase {
	//ordem status Processing Payment / Confirmed Payment
	@Autowired
	OrderRepository orderRepository;
	
	public Mono<Void> execute(Long id, Order orderModel) {
        log.info("Update Order with ID: {}", id);
        //vamos fazer um validStatus -> cancelamento -> extorno
        //primeiroInsert na base de pagamento -> update order Status payment processing
        //Se status confirmado update status da ordem -> notificatodos ->processing
        return orderRepository.updateOrder(id, orderModel)
            .doOnSuccess(saved -> log.info("SUCCESS! Order has been Update with ID: {}", orderModel.getId()))
            .then(); // Converte Mono<Order> para Mono<Void>
    }
	
	//validStatus -> se for invalido ja vai pra expurgo algum fluxo de tratativa
	
	

}
