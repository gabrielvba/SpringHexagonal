package com.github.gabrielvba.ms_order_management.domain.rules;

import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.OrderStatus;
import com.github.gabrielvba.ms_order_management.domain.model.PaymentStatus;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ChangeStatus {

	public Order execute(Order order, Order retrievedOrder ) {
		creationRules(order, retrievedOrder);
		if(retrievedOrder != null) {
			paymentRules(order, retrievedOrder);
			shippingStartRules(order, retrievedOrder);
			deliveryStartRules(order, retrievedOrder);
			cancelOrderRules(order, retrievedOrder);
			completedRules(order, retrievedOrder);

		}
		return order;
	}

	public void creationRules(Order order, Order retrievedOrder ) {
		if (order != null && order.getOrderId() == null && order.getStatus() == null) {
			order.setStatus(OrderStatus.CREATED);
		}
	}

	public void paymentRules(Order order, Order retrievedOrder) {
		if (retrievedOrder.getPayment() == null) {
			order.setStatus(OrderStatus.PENDING_PAYMENT);
		}
		if (order != null && order.getPayment() != null && order.getPayment().getStatus() == PaymentStatus.COMPLETED && order.getShipmentId() == null) {
			order.setStatus(OrderStatus.PAYMENT_CONFIRMED);
		}
	}
	

	public void shippingStartRules(Order order, Order retrievedOrder) {
		if (order != null && retrievedOrder.getShipmentId() == null && order.getShipmentId() != null) {
			order.setStatus(OrderStatus.SHIPPING);
		}
	}
	
	public void deliveryStartRules(Order order, Order retrievedOrder) {
		if (order != null && retrievedOrder.getEstimatedDeliveryDate() == null && order.getEstimatedDeliveryDate() != null) {
			order.setStatus(OrderStatus.IN_ROUTE);
		}
	}
	
	public void completedRules(Order order, Order retrievedOrder) {
		if (order != null &&  order.getCompletionDate() != null && retrievedOrder.getCompletionDate() == null) {
			order.setStatus(OrderStatus.COMPLETED);
		}
	}
	
	public void cancelOrderRules(Order order, Order retrievedOrder) {
		if (order != null && order.getStatus() == OrderStatus.CANCELED
				|| order.getPayment() != null && (order.getPayment().getStatus() == PaymentStatus.CANCELED
						|| order.getPayment().getStatus() == PaymentStatus.FAILED
						|| order.getPayment().getStatus() == PaymentStatus.REFUNDED)) {
			order.setStatus(OrderStatus.CANCELED);
		}
	}

}
