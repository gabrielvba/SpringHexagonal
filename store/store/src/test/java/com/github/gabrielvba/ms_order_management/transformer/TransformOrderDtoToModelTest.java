package com.github.gabrielvba.ms_order_management.transformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.gabrielvba.ms_order_management.domain.exception.InvalidStatusException;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.transformer.TransformOrderDtoToModel;

import common.JsonReader;
import common.OrderComparator;

@ExtendWith(MockitoExtension.class)
public class TransformOrderDtoToModelTest {

	@InjectMocks
	private TransformOrderDtoToModel transformOrderDtoToModel;

	private OrderComparator orderComparator;
	
	private JsonReader jsonReader;
	
    @BeforeEach
    public void setup() {
        this.orderComparator = new OrderComparator();
		this.jsonReader = new JsonReader();
    }
    
    
	@Test
	void transformOrderDto_FullWithValidData_OrderModel() throws Exception {
		
		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"mock/transformer/orderDtoTransformerTest.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		Order transformedOrderModel = transformOrderDtoToModel.toModel(orderDtoIn);

		Order orderModelOut = jsonReader.readFromJson(
				"mock/transformer/orderModelTransformerTest.json",
				Order.class);

		orderComparator.compareOrderModel(transformedOrderModel, orderModelOut);
	}
	
	@Test
	void transformOrderDto_OrderStatusNUll_OrderModel() throws Exception {

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"mock/transformer/orderDtoTransformerOrderStatusNull.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		Order transformedOrderModel = transformOrderDtoToModel.toModel(orderDtoIn);


		Assertions.assertEquals(transformedOrderModel.getStatus(), null);
	}
	
	@Test
	void transformOrderDto_PaymentStatusNull_OrderModel() throws Exception {

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"mock/transformer/orderDtoTransformerPaymentStatusNull.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		Order transformedOrderModel = transformOrderDtoToModel.toModel(orderDtoIn);


		Assertions.assertEquals(transformedOrderModel.getPayment().getStatus(), null);
	}

	@Test
	void transformOrderDto_OrderStatusInvalid_OrderModel() throws Exception {

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"mock/transformer/orderDtoTransformerOrderStatusException.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);
	   
		Exception exception = Assertions.assertThrows(InvalidStatusException.class, () -> {
            transformOrderDtoToModel.toModel(orderDtoIn);
        });

		Assertions.assertEquals(exception.getMessage(), "Order Status inválido: 1");
	}
	
	@Test
	void transformOrderDto_PaymentStatusInvalid_OrderModel() throws Exception {

		com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order orderDtoIn = jsonReader.readFromJson(
						"mock/transformer/orderDtoTransformerPaymentStatusException.json",
						com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order.class);

		Exception exception = Assertions.assertThrows(InvalidStatusException.class, () -> {
            transformOrderDtoToModel.toModel(orderDtoIn);
        });

		Assertions.assertEquals(exception.getMessage(), "Payment Status inválido: 1");
	}

}