package com.gabriel.vba.store.infrastructure.adapter.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gabriel.vba.store.application.port.out.BDRepository;
import com.gabriel.vba.store.domain.model.Order;
import com.gabriel.vba.store.domain.model.Product;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class BDGateway implements BDRepository {

	Map<Long, Order> orderTb = new HashMap<Long, Order>();
	Map<Long, Product> productTb = new HashMap<Long, Product>();

	@Override
	public Order saveOrder(Order orderModel) {
        log.info("saving new order");
        orderTb.put(orderModel.getId(), orderModel);
		return orderModel;
	}

	@Override
	public List<Order> getAllOrders() {
        log.info("find all orders");
		List<Order> out = new ArrayList<Order>();
		orderTb.values().forEach(e -> out.add(e));
		return out;
	}

	@Override
	public void deleteOrder(Long id) {
        log.info("delete order with id", id);
		orderTb.remove(id);
	}

	@Override
	public Order updateOrder(Long id, Order orderModel) {
        log.info("Update order with id", orderModel.getId());
		orderTb.remove(orderModel.getId());
		orderTb.put(orderModel.getId(),orderModel);
		return orderModel;
	}

	
	
	@Override
	public List<Product> getAllProducts() {
        log.info("find all products");
		List<Product> out = new ArrayList<Product>();
		productTb.values().forEach(e -> out.add(e));
		return out;
	}

	@Override
	public Product saveProduct(Product productModel) {
        log.info("saving new product");
        productTb.put(productModel.getProdutoId(), productModel);	
        return productModel;
	}

	@Override
	public void deleteProduct(Long id) {
	    log.info("delete product with id", id);
	    productTb.remove(id);		
	}

	@Override
	public Product updateProduct(Long id, Product productModel) {
        log.info("Update product with id", id);
        productTb.remove(id);
        productTb.put(id,productModel);	
        return productModel;
	}
	
	@Override
	public List<Product> getProducts(List<Product> products) {
        log.info("find products");

		List<Product> out = new ArrayList<Product>();
		products.forEach(i -> {
			Product p = productTb.get(i.getProdutoId());
			if(p != null)out.add(p);
			
		});
		return out;
	}

}
