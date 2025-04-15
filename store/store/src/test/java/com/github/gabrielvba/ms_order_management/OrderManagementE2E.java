package com.github.gabrielvba.ms_order_management;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.gabrielvba.ms_order_management.application.port.out.ProductServicePort;
import com.github.gabrielvba.ms_order_management.application.port.out.StockServicePort;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.factory.ProductFactory;
import com.github.gabrielvba.ms_order_management.factory.StockAvailabilityFactory;

import common.JsonReader;
import common.OrderComparator;

@ActiveProfiles("it")
@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestPropertySource(properties = {
//        "spring.datasource.url=jdbc:tc:mysql:8.0.29:///testdb",
//        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
//        "spring.datasource.username=test",
//        "spring.datasource.password=test",
//        "spring.jpa.hibernate.ddl-auto=create-drop"
//})
//@Sql(scripts = { "/import_planets.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = { "/remove_planets.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class OrderManagementE2E {

	@Autowired
	private TestRestTemplate restTemplate;

//	nao precisamos pois o spring vai subir totalmente so precisamos mockar as conexoes externas
//	@Autowired
//	private OrderRepositoryPort orderRepositoryPort;

	@MockitoBean
	private ProductServicePort productServicePort;

	@MockitoBean
	private StockServicePort stockServicePort;

//	@Autowired
//	private TestRestTemplate restTemplate;

	private OrderComparator orderComparator;
	private JsonReader jsonReader;

	@BeforeEach
	public void setup() {
		this.orderComparator = new OrderComparator();
		this.jsonReader = new JsonReader();
	}
	
//	private static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.26")
//            .withDatabaseName("testdb")
//            .withUsername("test")
//            .withPassword("test");
//
//    @DynamicPropertySource
//    static void configureTestContainers(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mysql::getJdbcUrl);
//        registry.add("spring.datasource.username", mysql::getUsername);
//        registry.add("spring.datasource.password", mysql::getPassword);
//    }

//	@Test
//	void testOrderCreate() {
//	    ResponseEntity<OrderResponse> response = restTemplate.postForEntity("/orders/create", request, OrderResponse.class);
//	    assertEquals(HttpStatus.CREATED, response.getStatusCode());
//	}
	

	@Test
	void createOrderE2E_FullWithValidData_OrderModel() throws Exception {

		when(productServicePort.getProducts(Mockito.anyList()))
				.thenReturn(List.of(ProductFactory.createProduct(), ProductFactory.createProduct()));
		when(stockServicePort
				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
				.thenReturn(StockAvailabilityFactory.createStockAvailability());

		String orderJsonRequest = jsonReader.readJsonAsString("mock/orderDtoStart.json");
		Order orderModelOut = jsonReader.readFromJson("mock/orderModelStart.json", Order.class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(orderJsonRequest, headers);
		ResponseEntity<Order> response = restTemplate.postForEntity("/orders/create", request, Order.class);

		assertNotNull(response.getBody(), "O corpo da resposta não deveria ser nulo.");
		orderComparator.compareOrderModel(response.getBody(), orderModelOut);

	}

//	@Test
//	void createOrder_NoItems_400() throws Exception {
//		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(List.of());
//
//		String orderJsonRequest = jsonReader.readJsonAsString("mock/exception/invalidItemsNull.json");
//
//		restTemplate
//				.getForEntity(post("/orders/create").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
//				.andExpect(status().isNotFound()).andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.toString()))
//				.andExpect(jsonPath("$.message").value("Nenhum produto encontrado"));
//	}
//
//	@Test
//	void createOrder_OneItemInvalid_400() throws Exception {
//
//		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(List.of(ProductFactory.createProduct()));
//
//		String orderJsonRequest = jsonReader.readJsonAsString("mock/exception/invalidItemsPartial.json");
//
//		restTemplate
//				.getForEntity(post("/orders/create").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
//				.andExpect(status().isNotFound()).andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.toString()))
//				.andExpect(jsonPath("$.message").value("Produto indisponivel"));
//	}
//
//	@Test
//	void createOrder_AllItemInvalid_400() throws Exception {
//		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(null);
//		String orderJsonRequest = jsonReader.readJsonAsString("mock/exception/invalidItemsTotal.json");
//
//		restTemplate
//				.getForEntity(post("/orders/create").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
//				.andExpect(status().isNotFound()).andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.toString()))
//				.andExpect(jsonPath("$.message").value("Nenhum produto encontrado"));
//	}
//
//	@Test
//	void createOrder_ItemInvalidOnStock_400() throws Exception {
//		when(productServicePort.getProducts(Mockito.anyList())).thenReturn(List.of(ProductFactory.createProduct()));
//		when(stockServicePort
//				.getOrderAvailibility(Mockito.any(com.github.gabrielvba.ms_order_management.domain.model.Order.class)))
//				.thenReturn(StockAvailabilityFactory.createStockAvailabilityFalse());
//		String orderJsonRequest = jsonReader.readJsonAsString("mock/exception/invalidStock.json");
//
//		restTemplate
//				.getForEntity(post("/orders/create").contentType(MediaType.APPLICATION_JSON).content(orderJsonRequest))
//				.andExpect(status().isNotFound()).andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.toString()))
//				.andExpect(jsonPath("$.message").value("Produto indisponivel no stock"));
//	}

}
