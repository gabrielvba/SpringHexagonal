package com.github.gabrielvba.ms_order_management.controller;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.application.port.out.ProductServicePort;
import com.github.gabrielvba.ms_order_management.application.port.out.StockServicePort;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.factory.ProductFactory;
import com.github.gabrielvba.ms_order_management.factory.StockAvailabilityFactory;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.OrderController;

import common.JsonReader;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
@WebMvcTest(OrderController.class)
public class OrderControllerSpringMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OrderRepositoryPort orderRepositoryPort;

	@MockitoBean
	private ProductServicePort productServicePort;

	@MockitoBean
	private StockServicePort stockServicePort;

	private JsonReader jsonReader;

	@BeforeEach
	public void setup() {
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
		String orderJsonRequest = jsonReader.readJsonAsString("mock/orderDtoStart.json");

		mockMvc.perform(post("/orders/create").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.orderId").value(notNullValue()));
	}

	@Test
	void paymentPending_FullWithValidData_OrderModel() throws Exception {

		when(productServicePort.getProducts(Mockito.anyList()))
				.thenReturn(List.of(ProductFactory.createProduct(), ProductFactory.createProduct()));
		when(stockServicePort
				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
				.thenReturn(StockAvailabilityFactory.createStockAvailability());
		when(orderRepositoryPort.updateOrder(Mockito.any(Long.class),Mockito.any(Order.class)))
				.thenAnswer(invocation -> {
			        Order order = invocation.getArgument(1);
			        return order;
			    });
		when(orderRepositoryPort.getOrder(Mockito.any(Long.class))).thenReturn(new Order());


		String orderJsonRequest = jsonReader.readJsonAsString("mock/orderDtoPaymentPending.json");

		mockMvc.perform(post("/orders/payment/1").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isOk())
	            .andExpect(jsonPath("$.payment").exists())  // agora deve existir
				.andExpect(jsonPath("$.status").value("PENDING_PAYMENT"));
	}
	
	@Test
	void paymentCompleted_FullWithValidData_OrderModel() throws Exception {

		when(productServicePort.getProducts(Mockito.anyList()))
				.thenReturn(List.of(ProductFactory.createProduct(), ProductFactory.createProduct()));
		when(stockServicePort
				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
				.thenReturn(StockAvailabilityFactory.createStockAvailability());
		when(orderRepositoryPort.updateOrder(Mockito.any(Long.class),Mockito.any(Order.class)))
				.thenAnswer(invocation -> {
			        Order order = invocation.getArgument(1);
			        return order;
			    });
		when(orderRepositoryPort.getOrder(Mockito.any(Long.class))).thenReturn(new Order());


		String orderJsonRequest = jsonReader.readJsonAsString("mock/orderDtoPaymentCompleted.json");

		mockMvc.perform(post("/orders/payment/1").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isOk())
	            .andExpect(jsonPath("$.payment").exists())  // agora deve existir
				.andExpect(jsonPath("$.status").value("PAYMENT_CONFIRMED"));
	}
	
	@Test
	void paymentPicking_FullWithValidData_OrderModel() throws Exception {

		when(productServicePort.getProducts(Mockito.anyList()))
				.thenReturn(List.of(ProductFactory.createProduct(), ProductFactory.createProduct()));
		when(stockServicePort
				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
				.thenReturn(StockAvailabilityFactory.createStockAvailability());
		when(orderRepositoryPort.updateOrder(Mockito.any(Long.class),Mockito.any(Order.class)))
				.thenAnswer(invocation -> {
			        Order order = invocation.getArgument(1);
			        return order;
			    });
		when(orderRepositoryPort.getOrder(Mockito.any(Long.class))).thenReturn(new Order());


		String orderJsonRequest = jsonReader.readJsonAsString("mock/orderDtoPicking.json");

		mockMvc.perform(post("/orders/picking/1").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isOk())
	            .andExpect(jsonPath("$.payment").exists())  // agora deve existir
				.andExpect(jsonPath("$.status").value("SHIPPING"));
	}
	
	@Test
	void paymentShipping_FullWithValidData_OrderModel() throws Exception {

		when(productServicePort.getProducts(Mockito.anyList()))
				.thenReturn(List.of(ProductFactory.createProduct(), ProductFactory.createProduct()));
		when(stockServicePort
				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
				.thenReturn(StockAvailabilityFactory.createStockAvailability());
		when(orderRepositoryPort.updateOrder(Mockito.any(Long.class),Mockito.any(Order.class)))
				.thenAnswer(invocation -> {
			        Order order = invocation.getArgument(1);
			        return order;
			    });
		when(orderRepositoryPort.getOrder(Mockito.any(Long.class))).thenReturn(new Order());


		String orderJsonRequest = jsonReader.readJsonAsString("mock/orderDtoShipping.json");

		mockMvc.perform(post("/orders/shipped/1").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isOk())
	            .andExpect(jsonPath("$.payment").exists())  // agora deve existir
				.andExpect(jsonPath("$.status").value("IN_ROUTE"));
	}
	
	@Test
	void paymentCanceled_FullWithValidData_OrderModel() throws Exception {

		when(productServicePort.getProducts(Mockito.anyList()))
				.thenReturn(List.of(ProductFactory.createProduct(), ProductFactory.createProduct()));
		when(stockServicePort
				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
				.thenReturn(StockAvailabilityFactory.createStockAvailability());
		when(orderRepositoryPort.updateOrder(Mockito.any(Long.class),Mockito.any(Order.class)))
				.thenAnswer(invocation -> {
			        Order order = invocation.getArgument(1);
			        return order;
			    });
		when(orderRepositoryPort.getOrder(Mockito.any(Long.class))).thenReturn(new Order());


		String orderJsonRequest = jsonReader.readJsonAsString("mock/orderDtoCancel.json");

		mockMvc.perform(post("/orders/cancel/1").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isOk())
//	            .andExpect(jsonPath("$.payment").exists())  // agora deve existir
				.andExpect(jsonPath("$.status").value("CANCELED"));
	}
	@Test
	void createOrder_NoItems_400() throws Exception {
		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(List.of());

		String orderJsonRequest = jsonReader.readJsonAsString("mock/exception/invalidItemsNull.json");

		mockMvc.perform(post("/orders/create").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.toString()))
				.andExpect(jsonPath("$.message").value("Nenhum produto encontrado"));
	}

	@Test
	void createOrder_OneItemInvalid_400() throws Exception {

		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(List.of(ProductFactory.createProduct()));

		String orderJsonRequest = jsonReader.readJsonAsString("mock/exception/invalidItemsPartial.json");

		mockMvc.perform(post("/orders/create").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.toString()))
				.andExpect(jsonPath("$.message").value("Produto indisponivel"));
	}

	@Test
	void createOrder_AllItemInvalid_400() throws Exception {
		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(null);
		String orderJsonRequest = jsonReader.readJsonAsString("mock/exception/invalidItemsTotal.json");

		mockMvc.perform(post("/orders/create").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.toString()))
				.andExpect(jsonPath("$.message").value("Nenhum produto encontrado"));
	}

	@Test
	void createOrder_ItemInvalidOnStock_400() throws Exception {
		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(List.of(ProductFactory.createProduct()));
		when(stockServicePort
				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
				.thenReturn(StockAvailabilityFactory.createStockAvailabilityFalse());
		String orderJsonRequest = jsonReader.readJsonAsString("mock/exception/invalidStock.json");

		mockMvc.perform(post("/orders/create").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.toString()))
				.andExpect(jsonPath("$.message").value("Produto indisponivel no stock"));
	}

}