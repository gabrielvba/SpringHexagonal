package com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.gabrielvba.ms_order_management.application.port.in.CancelOrderUserCase;
import com.github.gabrielvba.ms_order_management.application.port.in.ConsultOrderUseCase;
import com.github.gabrielvba.ms_order_management.application.port.in.CreateOrderUserCase;
import com.github.gabrielvba.ms_order_management.application.port.in.OrderPickingUseCase;
import com.github.gabrielvba.ms_order_management.application.port.in.OrderShippingUseCase;
import com.github.gabrielvba.ms_order_management.application.port.in.PaymentProcessUseCase;
import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.application.port.out.ProductServicePort;
import com.github.gabrielvba.ms_order_management.application.port.out.StockServicePort;
import com.github.gabrielvba.ms_order_management.domain.rules.ChangeStatus;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.transformer.TransformOrderDtoToModel;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderRepositoryPort productRepository;
	private final ProductServicePort productServicePort;
	private final StockServicePort stockServicePort;
	private final ChangeStatus changeStatus;

	@Autowired
    public OrderController(OrderRepositoryPort productRepository, ProductServicePort productServicePort,
			StockServicePort stockServicePort) {
		super();
		this.productRepository = productRepository;
		this.productServicePort = productServicePort;
		this.stockServicePort = stockServicePort;
		this.changeStatus = new ChangeStatus();
	}

	@PostMapping(path = "/create")
	public ResponseEntity<Object> createOrder(@RequestBody Order order) {
	    log.info("Order Received: {}", order);

	    try {
	        com.github.gabrielvba.ms_order_management.domain.model.Order orderModel =
	                TransformOrderDtoToModel.toModel(order);

	        com.github.gabrielvba.ms_order_management.domain.model.Order createdOrder =
	                new CreateOrderUserCase(productRepository, stockServicePort, productServicePort, changeStatus)
	                        .execute(orderModel);

	        // Aqui você pode construir a URI do recurso criado, caso tenha um ID
	        URI location = URI.create("/orders/" + createdOrder.getOrderId());

	        return ResponseEntity.created(location).body(createdOrder);

	    } catch (Exception ex) {
	        log.error("Error creating order: {}", ex.getMessage(), ex);
	        return ResponseEntity.badRequest().body("Error creating order: " + ex.getMessage());
	    }
	}
    
    @PostMapping(path = "/payment/{id}")
    public ResponseEntity<Object> updateOrderPaymentProcess(@PathVariable Long id, @RequestBody Order order){
        log.info("Order Received: {}", order);

    	com.github.gabrielvba.ms_order_management.domain.model.Order orderModel = null;
        try {
        	orderModel = TransformOrderDtoToModel.toModel(order);
        	new PaymentProcessUseCase(productRepository, changeStatus).execute(id, orderModel);
        } catch (Exception ex) {
        	return ResponseEntity.badRequest().body(ex.getMessage());
        }
        
        return ResponseEntity.ok().build();
    }
    
    @PostMapping(path = "/picking/{id}")
    public ResponseEntity<Object> updateOrderPicking(@PathVariable Long id, @RequestBody Order order) {
        log.info("Order Received: {}", order);
        

    	com.github.gabrielvba.ms_order_management.domain.model.Order orderModel = null;
        try {
        	orderModel = TransformOrderDtoToModel.toModel(order);
        	new OrderPickingUseCase(productRepository,stockServicePort,changeStatus).execute(id, orderModel);
        } catch (Exception ex) {
        	return ResponseEntity.badRequest().body(ex.getMessage());
        }
        
        return ResponseEntity.ok().build();
    }
    
    @PostMapping(path = "/shipped/{id}")
    public ResponseEntity<Object> updateOrderShipping(@PathVariable Long id, @RequestBody Order order) {
        log.info("Order Received: {}", order);

    	com.github.gabrielvba.ms_order_management.domain.model.Order orderModel = null;
        try {
        	orderModel = TransformOrderDtoToModel.toModel(order);
        	new OrderShippingUseCase(productRepository,stockServicePort,changeStatus).execute(id, orderModel);
        } catch (Exception ex) {
        	return ResponseEntity.badRequest().body(ex.getMessage());
        }
        
        return ResponseEntity.ok().build();
    }
    
    @PostMapping(path = "/cancel/{id}")
    public ResponseEntity<Object> cancelOrder(@PathVariable Long id, @RequestBody Order order) {
        log.info("Order Received: {}", order);

    	com.github.gabrielvba.ms_order_management.domain.model.Order orderModel = null;
    	new CancelOrderUserCase(productRepository, stockServicePort, changeStatus).execute(id, orderModel);
        try {
        	orderModel = TransformOrderDtoToModel.toModel(order);
        } catch (Exception ex) {
        	return ResponseEntity.badRequest().body(ex.getMessage());
        }
        
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<List<com.github.gabrielvba.ms_order_management.domain.model.Order>> listarPedidos() {
        List<com.github.gabrielvba.ms_order_management.domain.model.Order> pedidos = new ConsultOrderUseCase(productRepository,changeStatus).execute();
        return ResponseEntity.ok(pedidos);
    }
    
}