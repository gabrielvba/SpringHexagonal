package com.gabriel.vba.store.application.port.out;

import java.util.List;

import com.gabriel.vba.store.domain.model.Order;
import com.gabriel.vba.store.domain.model.Product;

public interface BDRepository {

	Order saveOrder(Order orderModel);
	List<Order> getAllOrders();
	void deleteOrder(Long id);	
	Order updateOrder(Long id, Order orderModel);	

	Product saveProduct(Product orderModel);	
	List<Product> getAllProducts();
	void deleteProduct(Long id);	
	Product updateProduct(Long id, Product orderModel);	
	List<Product> getProducts(List<Product> itens);
}
