package com.github.gabrielvba.ms_order_management.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.github.gabrielvba.ms_order_management.domain.dto.ProductDTO;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Payment;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.ProductItem;

public class ProductFactory {

//	public static Order createPaymentOrder() {
//		return new Order(12345l, 67890L, List.of(createProductItem()), new BigDecimal("199.99"), "created", "123 Street", createPayment(), "PROMO123", null, null, null);
//	}
//
//	public static ProductItem createProductItem() {
//		return new ProductItem(1L, 1001L, "Product Name", new BigDecimal("20.50"), 2, "PROMO123");
//	}
//
//	public static Payment createPayment() {
//		return new Payment(1L, 1L, 123456789L, "Credit Card", "Completed", LocalDateTime.now(), new BigDecimal("150.75"));
//
//	}

	public static ProductDTO createProduct() {
		return new ProductDTO(1001L, new BigDecimal("29.99"), "Product Name", "Product Description");

	}
}
