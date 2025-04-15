package com.github.gabrielvba.ms_order_management.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.github.gabrielvba.ms_order_management.domain.exception.ProductItemException;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.OrderController;

import common.JsonReader;
import common.OrderComparator;
import common.OrderRepositoryMock;
import common.ProductServiceMock;
import common.StockServiceMock;

public class OrderControllerTest {

	private OrderController orderController;

	private OrderRepositoryMock orderRepositoryMock;

	private ProductServiceMock productServiceMock;

	private StockServiceMock stockServiceMock;

	private OrderComparator orderComparator;

	
	public OrderControllerTest() {
		super();
		this.orderRepositoryMock = new OrderRepositoryMock();
		this.productServiceMock = new ProductServiceMock();
		this.stockServiceMock = new StockServiceMock();
		this.orderController = new OrderController(this.orderRepositoryMock, this.productServiceMock, this.stockServiceMock);
		this.orderComparator = new OrderComparator();
	}
	
    
	@Test
	void createOrder_FullWithValidData_OrderModel() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader
				.readFromJson(
						"mock/orderDtoStart.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		Order expectedOrderModelOut = jsonReader.readFromJson(
				"mock/orderModelStart.json",
				Order.class);
		
		ResponseEntity<Object> responseEntity = orderController.createOrder(orderDtoIn);
		Order outputOrder = (Order) responseEntity.getBody();
		orderComparator.compareOrderModel(outputOrder, expectedOrderModelOut);

	}

	@Test
	void createOrder_NoItems_400() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"mock/exception/invalidItemsNull.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);
	   
		ProductItemException thrown = Assertions.assertThrows(ProductItemException.class,
				() -> orderController.createOrder(orderDtoIn));

		Assertions.assertEquals(thrown.getMessage(), "Nenhum produto encontrado");
	}
	
	@Test
	void createOrder_OneItemInvalid_400() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader
				.readFromJson("mock/exception/invalidItemsPartial.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		ProductItemException thrown = Assertions.assertThrows(ProductItemException.class,
				() -> orderController.createOrder(orderDtoIn));

		Assertions.assertEquals(thrown.getMessage(), "Produto indisponivel");
	}
	
	@Test
	void createOrder_AllItemInvalid_400() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"mock/exception/invalidItemsTotal.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);
	   
		ProductItemException thrown = Assertions.assertThrows(ProductItemException.class,
				() -> orderController.createOrder(orderDtoIn));

		Assertions.assertEquals(thrown.getMessage(), "Nenhum produto encontrado");
	}
	
	@Test
	void createOrder_ItemInvalidOnStock_400() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"mock/exception/invalidStock.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);
	   
		ProductItemException thrown = Assertions.assertThrows(ProductItemException.class,
				() -> orderController.createOrder(orderDtoIn));

		Assertions.assertEquals(thrown.getMessage(), "Produto indisponivel no stock");
	}
	
}