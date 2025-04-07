package com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.domain.dto.ProductDTO;
import com.github.gabrielvba.ms_order_management.domain.model.Order;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class OrderRepository implements OrderRepositoryPort {

	Map<Long, Order> orderTb = new HashMap<Long, Order>();
	Map<Long, ProductDTO> productTb = new HashMap<Long, ProductDTO>();

	@Override
	public Order saveOrder(Order orderModel) {
        log.info("saving new order");
        orderTb.put(orderModel.getOrderId(), orderModel);
		return orderModel;
	}

	@Override
	public Order getOrders(long id) {
		return orderTb.get(id);
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
        log.info("Update order with id", orderModel.getOrderId());
		orderTb.remove(orderModel.getOrderId());
		orderTb.put(orderModel.getOrderId(),orderModel);
		return orderModel;
	}

}
