package com.gabriel.vba.storeReact.application.port.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.vba.storeReact.application.port.out.BDRepository;
import com.gabriel.vba.storeReact.domain.model.Product;

import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@NoArgsConstructor
public class CrudProductUserCase {

	@Autowired
	BDRepository orderRepository;
	
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
