package com.gabriel.vba.storeReact.application.port.in;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.vba.storeReact.application.port.out.BDRepository;
import com.gabriel.vba.storeReact.domain.model.Order;
import com.gabriel.vba.storeReact.domain.model.Product;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@NoArgsConstructor
public class CreateOrderUserCase {

	@Autowired
	BDRepository orderRepository;

public Mono<Order> execute(Order orderModel) {
    return validOrder(orderModel)
            .then(orderRepository.saveOrder(orderModel))
            .thenReturn(orderModel) // Removido parêntese extra
            .onErrorResume(ex -> Mono.error(new Exception("Falha ao criar um pedido", ex)));
}

Mono<Void> validOrder(Order orderModel) {
    return orderRepository.getProducts(orderModel.getItens())
            .collectList()
            .flatMap(products -> {
                if (orderModel.getItens().size() > products.size()) {
                    return Mono.error(new Exception("Produto não existe"));
                }
                return Mono.empty();
            });
}
}
