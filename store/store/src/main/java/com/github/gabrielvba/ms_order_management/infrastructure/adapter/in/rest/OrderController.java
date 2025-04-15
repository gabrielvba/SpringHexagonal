package com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	private final TransformOrderDtoToModel transformOrderDtoToModel;

	public OrderController(OrderRepositoryPort productRepository, ProductServicePort productServicePort,
			StockServicePort stockServicePort) {
		super();
		this.productRepository = productRepository;
		this.productServicePort = productServicePort;
		this.stockServicePort = stockServicePort;
		this.changeStatus = new ChangeStatus();
		this.transformOrderDtoToModel = new TransformOrderDtoToModel();

	}

	@PostMapping(path = "/create")
	public ResponseEntity<Object> createOrder(@RequestBody Order order) {
		log.info("Order Received: {}", order);

		var createdOrder = new CreateOrderUserCase(productRepository, stockServicePort, productServicePort,
				changeStatus).execute(transformOrderDtoToModel.toModel(order));

		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
	}

	@PostMapping(path = "/payment/{id}")
	public ResponseEntity<Object> updateOrderPaymentProcess(@PathVariable Long id, @RequestBody Order order) {
		log.info("Order Received: {}", order);

		var updatePayment = new PaymentProcessUseCase(productRepository, changeStatus).execute(id, transformOrderDtoToModel.toModel(order));

		return ResponseEntity.ok().body(updatePayment);
	}

	@PostMapping(path = "/picking/{id}")
	public ResponseEntity<Object> updateOrderPicking(@PathVariable Long id, @RequestBody Order order) {
		log.info("Order Received: {}", order);

		var updatePayment =  new OrderPickingUseCase(productRepository, stockServicePort, changeStatus).execute(id,
				transformOrderDtoToModel.toModel(order));

		return ResponseEntity.ok().body(updatePayment);
	}

	@PostMapping(path = "/shipped/{id}")
	public ResponseEntity<Object> updateOrderShipping(@PathVariable Long id, @RequestBody Order order) {
		log.info("Order Received: {}", order);

		var updatedOrder =  new OrderShippingUseCase(productRepository, changeStatus).execute(id, transformOrderDtoToModel.toModel(order));

		return ResponseEntity.ok().body(updatedOrder);
	}

	@PostMapping(path = "/cancel/{id}")
	public ResponseEntity<Object> cancelOrder(@PathVariable Long id, @RequestBody Order order) {
		log.info("Order Received: {}", order);

		var updatedOrder = new CancelOrderUserCase(productRepository, stockServicePort, changeStatus).execute(id,
				transformOrderDtoToModel.toModel(order));

		return ResponseEntity.ok().body(updatedOrder);
	}

	@GetMapping
	public ResponseEntity<List<com.github.gabrielvba.ms_order_management.domain.model.Order>> listarPedidos() {
		var pedidos = new ConsultOrderUseCase(productRepository).execute();
		return ResponseEntity.ok(pedidos);
	}
}
