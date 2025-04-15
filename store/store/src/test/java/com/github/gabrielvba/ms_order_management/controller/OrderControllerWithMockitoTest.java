package com.github.gabrielvba.ms_order_management.controller;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.application.port.out.ProductServicePort;
import com.github.gabrielvba.ms_order_management.application.port.out.StockServicePort;
import com.github.gabrielvba.ms_order_management.domain.exception.ProductItemException;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.factory.ProductFactory;
import com.github.gabrielvba.ms_order_management.factory.StockAvailabilityFactory;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.OrderController;

import common.JsonReader;
import common.OrderComparator;

@ExtendWith(MockitoExtension.class)
public class OrderControllerWithMockitoTest {

	@InjectMocks
	OrderController orderController;

	@Mock
	private OrderRepositoryPort orderRepositoryPort;

	@Mock
	private ProductServicePort productServicePort;

	@Mock
	private StockServicePort stockServicePort;

	private OrderComparator orderComparator;
	
	private JsonReader jsonReader;
	
    @BeforeEach
    public void setup() {
        orderComparator = new OrderComparator();
		this.jsonReader = new JsonReader();
    }
    
	@Test
	void createOrder_FullWithValidData_OrderModel() throws Exception {

		when(productServicePort.getProducts(Mockito.anyList()))
				.thenReturn(List.of(ProductFactory.createProduct(), ProductFactory.createProduct()));
		when(stockServicePort
				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
				.thenReturn(StockAvailabilityFactory.createStockAvailability());
		when(orderRepositoryPort.saveOrder(Mockito.any(Order.class)))
	    .thenAnswer(invocation -> {
	        Order order = invocation.getArgument(0);
	        order.getItems().forEach(e -> e.setId(e.getExternalProductId()));
	        order.setOrderId(1l);
	        return order;
	    });
		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader
				.readFromJson("mock/orderDtoStart.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		com.github.gabrielvba.ms_order_management.domain.model.Order orderModelOut = jsonReader.readFromJson(
				"mock/orderModelStart.json", com.github.gabrielvba.ms_order_management.domain.model.Order.class);

		ResponseEntity<Object> responseEntity = orderController.createOrder(orderDtoIn);

		Order outputOrder = (Order) responseEntity.getBody();
		orderComparator.compareOrderModel(outputOrder, orderModelOut);
	}

	@Test
	void createOrder_NoItems_400() throws Exception {
		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(List.of());

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader
				.readFromJson("mock/exception/invalidItemsNull.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		ProductItemException thrown = Assertions.assertThrows(ProductItemException.class,
				() -> orderController.createOrder(orderDtoIn));

		Assertions.assertEquals(thrown.getMessage(), "Nenhum produto encontrado");
	}

	@Test
	void createOrder_OneItemInvalid_400() throws Exception {

		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(List.of(ProductFactory.createProduct()));

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader
				.readFromJson("mock/exception/invalidItemsPartial.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		ProductItemException thrown = Assertions.assertThrows(ProductItemException.class,
				() -> orderController.createOrder(orderDtoIn));

		Assertions.assertEquals(thrown.getMessage(), "Produto indisponivel");
	}

	@Test
	void createOrder_AllItemInvalid_400() throws Exception {
		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(null);

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader
				.readFromJson("mock/exception/invalidItemsTotal.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		ProductItemException thrown = Assertions.assertThrows(ProductItemException.class,
				() -> orderController.createOrder(orderDtoIn));

		Assertions.assertEquals(thrown.getMessage(), "Nenhum produto encontrado");

	}

	@Test
	void createOrder_ItemInvalidOnStock_400() throws Exception {
		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(List.of(ProductFactory.createProduct()));
		when(stockServicePort
				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
				.thenReturn(StockAvailabilityFactory.createStockAvailabilityFalse());

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader
				.readFromJson("mock/exception/invalidStock.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		ProductItemException thrown = Assertions.assertThrows(ProductItemException.class,
				() -> orderController.createOrder(orderDtoIn));

		Assertions.assertEquals(thrown.getMessage(), "Produto indisponivel no stock");
	}

}