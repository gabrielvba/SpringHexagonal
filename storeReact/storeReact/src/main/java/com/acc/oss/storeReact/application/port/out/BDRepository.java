package com.acc.oss.storeReact.application.port.out;

import java.util.List;

import com.acc.oss.storeReact.domain.model.Order;
import com.acc.oss.storeReact.domain.model.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BDRepository {

	Mono<Order> saveOrder(Order orderModel);
	Flux<Order> getAllOrders();
	Mono<Void> deleteOrder(Long id);	
	Mono<Order> updateOrder(Long id, Order orderModel);	

	Mono<Product> saveProduct(Product orderModel);	
	Flux<Product> getAllProducts();
	Mono<Void> deleteProduct(Long id);	
	Mono<Product> updateProduct(Long id, Product orderModel);	
	Flux<Product> getProducts(List<Product> itens);
}
