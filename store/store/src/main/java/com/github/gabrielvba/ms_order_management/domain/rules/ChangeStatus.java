package com.github.gabrielvba.ms_order_management.domain.rules;

import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.OrderStatus;
import com.github.gabrielvba.ms_order_management.domain.model.PaymentStatus;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ChangeStatus {

	public  Order execute(Order order, OrderStatus step) {
	    if (order == null || step == null) {
	        return order;  // Retorna a ordem sem alteração caso parâmetros inválidos.
	    }
	    log.info("Update Order with ID: {}");

	    // Converte o 'step' para o status desejado
	    switch (step) {

	        case OrderStatus.CREATED:
	        	log.info("entrou");
	            order.setStatus(OrderStatus.CREATED);
	            break;
	        case OrderStatus.PENDING_PAYMENT:
	            if (order.getPayment() != null && order.getPayment().getStatus() != null) {
	                order.setStatus(OrderStatus.PENDING_PAYMENT);
	                if (order.getPayment().getStatus() == PaymentStatus.COMPLETED) {
	                    order.setStatus(OrderStatus.PAYMENT_CONFIRMED);
	                }
	            }
	            break;
	        case OrderStatus.PAYMENT_CONFIRMED:
	            order.setStatus(OrderStatus.SHIPPING);
	            break;
	        case OrderStatus.SHIPPING:
	            order.setStatus(OrderStatus.IN_ROUTE);
	            break;
	        case OrderStatus.CANCELED:
	            order.setStatus(OrderStatus.CANCELED);
	            break;
	        default:
	            throw new IllegalArgumentException("Step " + step + " is not valid.");
	    }
	    return order;
	}
}
