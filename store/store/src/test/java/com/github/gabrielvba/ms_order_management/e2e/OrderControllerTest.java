package com.github.gabrielvba.ms_order_management.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.github.gabrielvba.ms_order_management.domain.exception.InvalidStatusException;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.Payment;
import com.github.gabrielvba.ms_order_management.domain.model.ProductItem;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.OrderController;
import com.github.gabrielvba.ms_order_management.port.out.OrderRepositoryMock;
import com.github.gabrielvba.ms_order_management.port.out.ProductServiceMock;
import com.github.gabrielvba.ms_order_management.port.out.StockServiceMock;
import com.github.gabrielvba.ms_order_management.util.JsonReader;

@SpringBootTest
public class OrderControllerTest {

	private OrderController orderController;

	private OrderRepositoryMock orderRepositoryMock;

	private ProductServiceMock productServiceMock;

	private StockServiceMock stockServiceMock;


	public OrderControllerTest() {
		super();
		this.orderRepositoryMock = new OrderRepositoryMock();
		this.productServiceMock = new ProductServiceMock();
		this.stockServiceMock = new StockServiceMock();
		this.orderController = new OrderController(this.orderRepositoryMock, this.productServiceMock, this.stockServiceMock);
	}
	
	@BeforeEach
	void setUp() {
//		orderController = new OrderController(orderRepositoryMock,productServiceMock,stockServiceMock);
//		orderController = new OrderController(new OrderRepositoryMock(),new ProductServiceMock(),new StockServiceMock());

	}


	@Test
	void testCreateOrder() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader
				.readFromJson(
						"C:/Users/User/OneDrive/Documentos/GIT/eclipse/SpringHexagonal/store/store/src/test/java/resource/mock/orderDtoStart.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		ResponseEntity<Object> transformedOrderModel = orderController.createOrder(orderDtoIn);


		Order orderModelOut = jsonReader.readFromJson(
				"C:/Users/User/OneDrive/Documentos/GIT/eclipse/SpringHexagonal/store/store/src/test/java/resource/mock/orderModelStart.json",
				Order.class);

		compareOrders(transformedOrderModel, orderModelOut);
	}

	@Test
	void testCreateOrderNullItems() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"C:/Users/User/OneDrive/Documentos/GIT/eclipse/SpringHexagonal/store/store/src/test/java/resource/mock/exception/invalidItemsNull.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);
	   
		ResponseEntity<Object> responseEntity = orderController.createOrder(orderDtoIn);


		Assertions.assertEquals(responseEntity.getBody(), "Error creating order: Nenhum produto encontrado");
	}
	
	@Test
	void testCreateOrderItemsPartialInvalid() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"C:/Users/User/OneDrive/Documentos/GIT/eclipse/SpringHexagonal/store/store/src/test/java/resource/mock/exception/invalidItemsPartial.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);
	   
		ResponseEntity<Object> responseEntity = orderController.createOrder(orderDtoIn);


		Assertions.assertEquals(responseEntity.getBody(), "Error creating order: Produto indisponivel");
	}
	
	@Test
	void testCreateOrderItemsTotalInvalid() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"C:/Users/User/OneDrive/Documentos/GIT/eclipse/SpringHexagonal/store/store/src/test/java/resource/mock/exception/invalidItemsTotal.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);
	   
		ResponseEntity<Object> responseEntity = orderController.createOrder(orderDtoIn);


		Assertions.assertEquals(responseEntity.getBody(), "Error creating order: Nenhum produto encontrado");
	}
	
	@Test
	void testCreateOrderItemsStockInvalid() throws Exception {
		JsonReader jsonReader = new JsonReader();

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"C:/Users/User/OneDrive/Documentos/GIT/eclipse/SpringHexagonal/store/store/src/test/java/resource/mock/exception/invalidStock.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);
	   
		ResponseEntity<Object> responseEntity = orderController.createOrder(orderDtoIn);


		Assertions.assertEquals(responseEntity.getBody(), "Error creating order: Produto indisponivel no stock");
	}
	
	private void compareOrders(ResponseEntity<Object> transformedOrderModel, Order orderModelOut) {
		Order transformedOrder = (Order) transformedOrderModel.getBody();
		// Comparando o orderId
		Assertions.assertEquals( transformedOrder.getOrderId(), orderModelOut.getOrderId());

		// Comparando o customerId
		Assertions.assertEquals(transformedOrder.getCustomerId(), orderModelOut.getCustomerId());

		// Comparando os itens (Produto)
		Assertions.assertEquals(transformedOrder.getItems().size(), orderModelOut.getItems().size());

		compareOrderItems(transformedOrder.getItems().get(0), orderModelOut.getItems().get(0));
		compareOrderItems(transformedOrder.getItems().get(1), orderModelOut.getItems().get(1));

		// Comparando o deliveryAddress
		Assertions.assertEquals(transformedOrder.getDeliveryAddress(), orderModelOut.getDeliveryAddress());

		// Comparando o promotionCode
		Assertions.assertEquals(transformedOrder.getPromotionCode(), orderModelOut.getPromotionCode());

		// Comparando o totalAmount
		Assertions.assertEquals(transformedOrder.getTotalAmount(), orderModelOut.getTotalAmount());

		// Comparando o status do pedido (OrderStatus)
		Assertions.assertEquals(transformedOrder.getStatus(), orderModelOut.getStatus());

		Assertions.assertEquals(transformedOrder.getPayment(), orderModelOut.getPayment());
		if(transformedOrder.getPayment() != null && orderModelOut.getPayment() != null) {
			compareOrderPayment(transformedOrder.getPayment(), orderModelOut.getPayment());
		}

		// Comparando o shipmentId
		Assertions.assertEquals(transformedOrder.getShipmentId(), orderModelOut.getShipmentId());

		// Comparando o estimatedDeliveryDate
		Assertions.assertEquals(transformedOrder.getEstimatedDeliveryDate(), orderModelOut.getEstimatedDeliveryDate());

		// Comparando o completionDate
		Assertions.assertEquals(transformedOrder.getCompletionDate(), orderModelOut.getCompletionDate());
	}

	private void compareOrderItems(ProductItem item1, ProductItem item2) {
		Assertions.assertEquals(item1.getId(), item2.getId(), "ProductItem IDs do not match");
		Assertions.assertEquals(item1.getExternalProductId(), item2.getExternalProductId(),
				"ExternalProductIds do not match");
		Assertions.assertEquals(item1.getName(), item2.getName(), "Product names do not match");
		Assertions.assertEquals(item1.getUnitPrice(), item2.getUnitPrice(), "Unit prices do not match");
		Assertions.assertEquals(item1.getQuantity(), item2.getQuantity(), "Quantities do not match");
		Assertions.assertEquals(item1.getPromotionCode(), item2.getPromotionCode(), "Promotion codes do not match");
	}

	private void compareOrderPayment(Payment payment1, Payment payment2) {
		Assertions.assertEquals(payment1.getExternal_transaction_id(), payment2.getExternal_transaction_id(),
				"External transaction IDs do not match");
		Assertions.assertEquals(payment1.getStatus(), payment2.getStatus(), "Payment statuses do not match");
		Assertions.assertEquals(payment1.getPaymentId(), payment2.getPaymentId(), "Payment IDs do not match");
		Assertions.assertEquals(payment1.getPaymentMethod(), payment2.getPaymentMethod(),
				"Payment methods do not match");
		Assertions.assertEquals(payment1.getOrderId(), payment2.getOrderId(), "Order IDs do not match");
		Assertions.assertEquals(payment1.getCompletionDate(), payment2.getCompletionDate(),
				"Completion dates do not match");
		Assertions.assertEquals(payment1.getTotalAmount(), payment2.getTotalAmount(), "Total amounts do not match");
	}


	
	
//	@Mock
//	private OrderRepositoryPort orderRepositoryPort;
//
//	@Mock
//	private ProductServicePort productServicePort;
//
//	@Mock
//	private StockServicePort stockServicePort;
	
	
//    ProductDTO product = createProduct();  // Garantir que createProduct() está criando o produto corretamente
//    List<ProductDTO> listDto = new ArrayList<>();
//    listDto.add(product);
//    
//    // Mockando o retorno de getProducts para os IDs fornecidos
//    when(productServicePort.getProducts(Mockito.anyList())).thenReturn(listDto);
//    
//    // Mockando o comportamento de saveOrder para retornar o mesmo objeto
//    when(orderRepositoryPort.saveOrder(Mockito.any(Order.class)))
//        .thenAnswer(invocation -> invocation.getArgument(0));  // Retorna o mesmo objeto passado como argumento
//    
//    // Mockando a validação de estoque
//    when(stockServicePort.validateInventory(Mockito.any(Order.class)))
//        .thenReturn(Mockito.any(Order.class));
	
	
//		public static Order createPaymentOrder() {
//			return new Order(1L, 1001L, List.of(createProductItem()), new BigDecimal("199.99"), "created", "123 Street",
//					createPayment(), "PROMO123", null, null, null);
//		
//		}
//		public static ProductItem createProductItem() {
//			return new ProductItem(1L, 1001L, "Product Name", new BigDecimal("20.50"), 2, "PROMO123");
//		}
//
//		public static Payment createPayment() {
//			return new Payment(1L, 1001L, 123456789L, "Credit Card", "Completed", LocalDateTime.now(),
//					new BigDecimal("150.75"));
//
//		}
//		
//		public static ProductDTO createProduct() {
//			return new ProductDTO(1001L, new BigDecimal("29.99"), "Product Name", "Product Description");
//
//		}


}