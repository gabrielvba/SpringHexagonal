package com.github.gabrielvba.ms_order_management_react.application.port.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management_react.application.port.out.OrderRepository;
import com.github.gabrielvba.ms_order_management_react.domain.model.Product;

import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@NoArgsConstructor
public class CrudProductUserCase {

	@Autowired
	OrderRepository orderRepository;
	
    public Mono<Product> criarProduto(Product productModel) {
		return orderRepository.saveProduct(productModel);
	}

	public Flux<Product> listarProdutos() {
		return orderRepository.getAllProducts();
	}

	public Mono<Product> atualizarProduto(Long id, Product productModel) {
		return orderRepository.updateProduct(id,productModel);
	}

	public Mono<Void> deletarProduto(Long id) {
		return orderRepository.deleteProduct(id);
	}
}
