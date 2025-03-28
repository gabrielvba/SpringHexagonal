package com.gabriel.vba.store.application.port.in;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.vba.store.application.port.out.BDRepository;
import com.gabriel.vba.store.domain.model.Order;
import com.gabriel.vba.store.domain.model.Product;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@NoArgsConstructor
public class CreateOrderUserCase {

	@Autowired
	BDRepository orderRepository;

	public Order execute(Order orderModel) throws Exception {
		try {
			this.validOrder(orderModel);

			this.persistOrder(orderModel);

			return orderModel;
		} catch (Exception ex) {
			throw new Exception("falha ao criar um pedido");
		}
	}

	private void persistOrder(Order orderModel) throws Exception {
		log.info("Creating new Order with ID: {}", orderModel.getId());
		orderRepository.saveOrder(orderModel);
		log.info("SUCCESS! Order has been CREATED with ID: {}", orderModel.getId());
	}

	private void validOrder(Order orderModel) throws Exception {
		List<Product> products = orderRepository.getProducts(orderModel.getItens());
		if (orderModel.getItens().size() > products.size()) {
			throw new Exception("Produto não existe");
		}
	}

	
	
	
	
	
	public List<Order> listarPedidos() {
		log.info("Find all Order");
		return orderRepository.getAllOrders();
	}

	public Product criarProduto(Product productModel) {
		return orderRepository.saveProduct(productModel);
	}

	public List<Product> listarProdutos() {
		return orderRepository.getAllProducts();
	}

	public Product atualizarProduto(Long id, Product productModel) {
		return orderRepository.updateProduct(id,productModel);
	}

	public boolean deletarProduto(Long id) {
		orderRepository.deleteProduct(id);
		return true;
	}
}
