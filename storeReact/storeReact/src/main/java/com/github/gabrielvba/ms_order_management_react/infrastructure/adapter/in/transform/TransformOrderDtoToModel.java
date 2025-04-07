package com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.in.transform;

import com.github.gabrielvba.ms_order_management_react.domain.model.Order;

public class TransformOrderDtoToModel {

	public static Order execute(com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.in.rest.dto.Order order) {
		Order orderModel = new Order();
		orderModel.setClienteId(order.getClienteId());
		orderModel.setEnderecoEntrega(order.getEnderecoEntrega());
//		orderModel.setFormaPagamento(order.getFormaPagamento());
		orderModel.setId(order.getId());
//		orderModel.setItens(order.getItens());
		orderModel.setStatus(order.getStatus());
		orderModel.setTotal(order.getTotal());
		return orderModel;
	}

}
