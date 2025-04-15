package com.github.gabrielvba.ms_order_management.application.port.in;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.application.port.out.ProductServicePort;
import com.github.gabrielvba.ms_order_management.application.port.out.StockServicePort;
import com.github.gabrielvba.ms_order_management.domain.dto.ProductDTO;
import com.github.gabrielvba.ms_order_management.domain.dto.StockAvailability;
import com.github.gabrielvba.ms_order_management.domain.exception.ProductItemException;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.ProductItem;
import com.github.gabrielvba.ms_order_management.domain.rules.ChangeStatus;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class CreateOrderUserCase {

	private final OrderRepositoryPort orderRepositoryPort;
	private final StockServicePort stockServicePort;
	private final ProductServicePort productServicePort;
	private final ChangeStatus changeStatus;

	public CreateOrderUserCase( OrderRepositoryPort orderRepositoryPort, 
								StockServicePort stockServicePort,
								ProductServicePort productServicePort,
								ChangeStatus changeStatus) {
		this.orderRepositoryPort = orderRepositoryPort;
		this.stockServicePort = stockServicePort;
		this.productServicePort = productServicePort;
		this.changeStatus = changeStatus;
	}

	public Order execute(Order orderModel) {
	    this.validProducts(orderModel);
	    this.validStock(orderModel);
	    changeStatus.execute(orderModel, null);
		return this.persistOrder(orderModel);
	}

	private Order persistOrder(Order orderModel) {
		orderRepositoryPort.saveOrder(orderModel);
		log.info("SUCCESS! Order has been CREATED with ID: {}", orderModel.getOrderId());
		return orderModel;
		
	}

	private void validProducts(Order orderModel) {
		List<ProductDTO> products = productServicePort.getProducts(
				orderModel.getItems().stream().map(ProductItem::getExternalProductId).toList());
		if (orderModel.getItems().size() == 0 || products  == null || products.isEmpty() ) {
			throw new ProductItemException("Nenhum produto encontrado");
		}
		if (orderModel.getItems().size() != products.size()) {
			throw new ProductItemException("Produto indisponivel");
		}
	}
	
	private void validStock(Order orderModel) {
		StockAvailability aviability = stockServicePort.getOrderAvailibility(orderModel);
		if (!aviability.available()) {
			throw new ProductItemException("Produto indisponivel no stock");
		}		
	}

}
