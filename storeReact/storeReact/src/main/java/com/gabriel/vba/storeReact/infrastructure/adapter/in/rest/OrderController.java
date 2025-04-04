package com.gabriel.vba.storeReact.infrastructure.adapter.in.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.vba.storeReact.application.port.in.CreateOrderUserCase;
import com.gabriel.vba.storeReact.application.port.in.CrudProductUserCase;
import com.gabriel.vba.storeReact.application.port.in.GetOrderUserCase;
import com.gabriel.vba.storeReact.application.port.in.UpdateOrderUserCase;
import com.gabriel.vba.storeReact.infrastructure.adapter.in.rest.dto.Order;
import com.gabriel.vba.storeReact.infrastructure.adapter.in.transform.TransformOrderDtoToModel;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class OrderController {

	@Autowired
	CreateOrderUserCase createOrderUserCase;
	
	@Autowired
	UpdateOrderUserCase updateOrderUserCase;
	
	@Autowired
	GetOrderUserCase getOrderUserCase;
	

    @PostMapping
    public Mono<ResponseEntity<Object>> criarPedido(@RequestBody Order order) {
        log.info("Order Received: {}", order);

        return Mono.fromCallable(() -> TransformOrderDtoToModel.execute(order))
            .flatMap(orderModel -> createOrderUserCase.execute(orderModel)
                .thenReturn(ResponseEntity.ok().build())
            )
            .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @GetMapping
    public Flux<com.gabriel.vba.storeReact.domain.model.Order> listarPedidos() {
        return getOrderUserCase.execute();
    }
    
    
    
}