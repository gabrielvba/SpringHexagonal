package com.acc.oss.storeReact.application.port.in;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acc.oss.storeReact.application.port.out.BDRepository;
import com.acc.oss.storeReact.domain.model.Order;
import com.acc.oss.storeReact.domain.model.Product;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@NoArgsConstructor
public class CreateOrderUserCase {

	@Autowired
	BDRepository orderRepository;

    public Mono<Order> execute(Order orderModel) {
        return validOrder(orderModel)
            .then(persistOrder(orderModel))
            .thenReturn(orderModel)
            .onErrorResume(ex -> Mono.error(new Exception("Falha ao criar um pedido", ex)));
    }

    private Mono<Void> persistOrder(Order orderModel) {
        log.info("Creating new Order with ID: {}", orderModel.getId());
        return orderRepository.saveOrder(orderModel)
            .doOnSuccess(saved -> log.info("SUCCESS! Order has been CREATED with ID: {}", orderModel.getId()))
            .then(); // Converte Mono<Order> para Mono<Void>
    }

    private Mono<Void> validOrder(Order orderModel) {
        return orderRepository.getProducts(orderModel.getItens())
            .collectList()
            .flatMap(products -> {
                if (orderModel.getItens().size() > products.size()) {
                    return Mono.error(new Exception("Produto não existe"));
                }
                return Mono.empty();
            });
    }

    public Flux<Order> listarPedidos() {
        log.info("Find all Orders");
        return orderRepository.getAllOrders();
    }
	
    
    
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
