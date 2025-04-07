package com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.in.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabrielvba.ms_order_management_react.application.port.in.CancelOrderUserCase;
import com.github.gabrielvba.ms_order_management_react.application.port.in.ConsultOrderUseCase;
import com.github.gabrielvba.ms_order_management_react.application.port.in.CreateOrderUserCase;
import com.github.gabrielvba.ms_order_management_react.application.port.in.OrderPickingUseCase;
import com.github.gabrielvba.ms_order_management_react.application.port.in.OrderShippedUseCase;
import com.github.gabrielvba.ms_order_management_react.application.port.in.PaymentProcessUseCase;
import com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.in.rest.dto.Order;
import com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.in.transform.TransformOrderDtoToModel;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	CreateOrderUserCase createOrderUserCase;
	
	@Autowired
	PaymentProcessUseCase paymentProcessUseCase;
	
	@Autowired
	OrderPickingUseCase orderPickingUseCase;
	
	@Autowired
	OrderShippedUseCase orderShippedUseCase;
	
	@Autowired
	CancelOrderUserCase cancelOrderUserCase;
	
	@Autowired
	ConsultOrderUseCase consultOrderUseCase;
	
    @PostMapping(path = "/create")
    public Mono<ResponseEntity<Object>> createOrder(@RequestBody Order order) {
        log.info("Order Received: {}", order);

        return Mono.fromCallable(() -> TransformOrderDtoToModel.execute(order))
            .flatMap(orderModel -> createOrderUserCase.execute(orderModel)
                .thenReturn(ResponseEntity.ok().build())
            )
            .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }

    @PostMapping(path = "/payment/{id}")
    public Mono<ResponseEntity<Object>> updateOrderPaymentProcess( @PathVariable Long id, @RequestBody Order order) {
        log.info("Order Received: {}", order);

        return Mono.fromCallable(() -> TransformOrderDtoToModel.execute(order))
            .flatMap(orderModel -> paymentProcessUseCase.execute(id, orderModel)
                .thenReturn(ResponseEntity.ok().build())
            )
            .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }
    
    @PostMapping(path = "/picking/{id}")
    public Mono<ResponseEntity<Object>> updateOrderPicking( @PathVariable Long id, @RequestBody Order order) {
        log.info("Order Received: {}", order);

        return Mono.fromCallable(() -> TransformOrderDtoToModel.execute(order))
            .flatMap(orderModel -> orderPickingUseCase.execute(id, orderModel)
                .thenReturn(ResponseEntity.ok().build())
            )
            .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }
    
    @PostMapping(path = "/shipped/{id}")
    public Mono<ResponseEntity<Object>> updateOrderShipped( @PathVariable Long id, @RequestBody Order order) {
        log.info("Order Received: {}", order);

        return Mono.fromCallable(() -> TransformOrderDtoToModel.execute(order))
            .flatMap(orderModel -> orderShippedUseCase.execute(id, orderModel)
                .thenReturn(ResponseEntity.ok().build())
            )
            .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }
    
    @PostMapping(path = "/cancel/{id}")
    public Mono<ResponseEntity<Object>> cancelOrder( @PathVariable Long id, @RequestBody Order order) {
        log.info("Order Received: {}", order);

        return Mono.fromCallable(() -> TransformOrderDtoToModel.execute(order))
            .flatMap(orderModel -> cancelOrderUserCase.execute(id, orderModel)
                .thenReturn(ResponseEntity.ok().build())
            )
            .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }
    
    @GetMapping
    public Flux<com.github.gabrielvba.ms_order_management_react.domain.model.Order> listarPedidos() {
        return consultOrderUseCase.execute();
    }
    
    
}