package com.github.gabrielvba.ms_order_management.application.port.in;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.application.port.out.ProductServicePort;
import com.github.gabrielvba.ms_order_management.application.port.out.StockServicePort;
import com.github.gabrielvba.ms_order_management.domain.dto.ProductDTO;
import com.github.gabrielvba.ms_order_management.domain.dto.StockAvailability;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.OrderStatus;
import com.github.gabrielvba.ms_order_management.domain.rules.ChangeStatus;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CreateOrderUserCase {

	private final OrderRepositoryPort orderRepository;
	private final StockServicePort stockServicePort;
	private final ProductServicePort productServicePort;
	private final ChangeStatus changeStatus;

	@Autowired
	public CreateOrderUserCase( OrderRepositoryPort orderRepository, 
								StockServicePort stockServicePort,
								ProductServicePort productServicePort,
								ChangeStatus changeStatus) {
		this.orderRepository = orderRepository;
		this.stockServicePort = stockServicePort;
		this.productServicePort = productServicePort;
		this.changeStatus = changeStatus;
	}

	public Order execute(Order orderModel) throws Exception {
	    log.info("Create Order");

	    this.validOrder(orderModel);
	    this.validStock(orderModel);
	    changeStatus.execute(orderModel, OrderStatus.CREATED);
	    
		try {
			return this.persistOrder(orderModel);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		} 
	}

	private Order persistOrder(Order orderModel) throws Exception {
		log.info("Creating new Order with ID: {}", orderModel.getOrderId());
		orderRepository.saveOrder(orderModel);
		log.info("SUCCESS! Order has been CREATED with ID: {}", orderModel.getOrderId());
		return orderModel;
		
	}

	private void validOrder(Order orderModel) throws Exception {
		List<ProductDTO> products = productServicePort.getProducts(
				orderModel.getItems().stream()
				.map(i -> i.getExternalProductId())
				.collect(Collectors.toList()));
		if (orderModel.getItems().size() == 0 || products  == null || products.isEmpty() ) {
			throw new Exception("Nenhum produto encontrado");
		}
		if (orderModel.getItems().size() != products.size()) {
			throw new Exception("Produto indisponivel");
		}
	}
	
	private void validStock(Order orderModel) throws Exception {
		StockAvailability aviability = stockServicePort.getOrderAvailibility(orderModel);
		if (!aviability.available()) {
			throw new Exception("Produto indisponivel no stock");
		}		
	}

}
