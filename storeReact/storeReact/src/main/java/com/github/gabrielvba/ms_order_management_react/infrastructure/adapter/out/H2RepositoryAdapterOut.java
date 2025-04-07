package com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management_react.application.port.out.OrderRepository;
import com.github.gabrielvba.ms_order_management_react.domain.model.Order;
import com.github.gabrielvba.ms_order_management_react.domain.model.Product;
import com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.out.implementation.OrderJpaRepository;
import com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.out.implementation.ProductJpaRepository;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Log4j2
@Component
public class H2RepositoryAdapterOut implements OrderRepository {
	@Autowired
	OrderJpaRepository orderJpaRepository;
	@Autowired
	ProductJpaRepository productJpaRepository;

	@Override
	public Mono<Order> saveOrder(Order orderModel) {
		log.info("Saving new order");
	    return Mono.fromCallable(() -> orderJpaRepository.save(orderModel))
	               .subscribeOn(Schedulers.boundedElastic()); // Executa em uma thread separada
	}

//	@Override
//	public Flux<Order> getAllOrders() {
//	    log.info("Find all orders");
//	    return Flux.defer(() -> Flux.fromIterable(orderJpaRepository.findAll()))
//	               .subscribeOn(Schedulers.boundedElastic());
//	}
//	
	@Override
	public Flux<Order> getAllOrders() {
	    log.info("Find all orders");
	    return Flux.fromIterable(orderJpaRepository.findAllWithProducts())
	               .map(order -> new Order(order.getId(), order.getClienteId(), order.getItens(),
	                                          order.getTotal(), order.getStatus(), order.getEnderecoEntrega(),
	                                          order.getFormaPagamento()));
	}

	@Override
	public Mono<Void> deleteOrder(Long id) {
	    log.info("Delete order with id: {}", id);
	    return Mono.fromCallable(() -> {
	        orderJpaRepository.deleteById(id);
	        return null;
	    }).subscribeOn(Schedulers.boundedElastic()).then();
	}

	@Override
	public Mono<Order> updateOrder(Long id, Order orderModel) {
	    log.info("Update order with id: {}", id);
	    return Mono.fromCallable(() -> orderJpaRepository.findById(id))
	               .flatMap(optionalOrder -> optionalOrder.map(existingOrder -> {
	                   existingOrder.setEnderecoEntrega(orderModel.getEnderecoEntrega());
	                   existingOrder.setFormaPagamento(orderModel.getFormaPagamento());
	                   existingOrder.setClienteId(orderModel.getClienteId());
	                   existingOrder.setItens(orderModel.getItens());
	                   existingOrder.setStatus(orderModel.getStatus());
	                   existingOrder.setTotal(orderModel.getTotal());
	                   return Mono.fromCallable(() -> orderJpaRepository.save(existingOrder))
	                              .subscribeOn(Schedulers.boundedElastic());
	               }).orElseGet(() -> Mono.error(new Exception("Order not found"))))
	               .subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Flux<Product> getAllProducts() {
	    log.info("Find all products");
	    return Flux.defer(() -> Flux.fromIterable(productJpaRepository.findAll()))
	               .subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Mono<Product> saveProduct(Product productModel) {
	    log.info("Saving new product");
	    return Mono.fromCallable(() -> productJpaRepository.save(productModel))
	               .subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Mono<Void> deleteProduct(Long id) {
	    log.info("Delete product with id: {}", id);
	    return Mono.fromRunnable(() -> productJpaRepository.deleteById(id))
	               .subscribeOn(Schedulers.boundedElastic())
	               .then();
	}

	@Override
	public Mono<Product> updateProduct(Long id, Product productModel) {
	    log.info("Update product with id: {}", id);
	    return Mono.fromCallable(() -> productJpaRepository.findById(id))
	               .flatMap(optionalProduct -> optionalProduct.map(existingProduct -> {
	                   existingProduct.setName(productModel.getName());
	                   existingProduct.setPrecoUnitario(productModel.getPrecoUnitario());
	                   existingProduct.setQuantidade(productModel.getQuantidade());
	                   return Mono.fromCallable(() -> productJpaRepository.save(existingProduct))
	                              .subscribeOn(Schedulers.boundedElastic());
	               }).orElseGet(() -> Mono.error(new Exception("Product not found"))))
	               .subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Flux<Product> getProducts(List<Product> products) {
	    log.info("Find products");
	    return Flux.defer(() -> Flux.fromIterable(productJpaRepository.findAllById(
	                      products.stream().map(Product::getProdutoId).toList()
	                  )))
	               .subscribeOn(Schedulers.boundedElastic());
	}
}
