package com.github.gabrielvba.ms_order_management.application.port.in;

import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.OrderStatus;
import com.github.gabrielvba.ms_order_management.domain.rules.ChangeStatus;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class PaymentProcessUseCase {
	
	private final OrderRepositoryPort orderRepository;
	private final ChangeStatus changeStatus;

	public PaymentProcessUseCase(OrderRepositoryPort orderRepository, ChangeStatus changeStatus) {
		this.orderRepository = orderRepository;
		this.changeStatus = changeStatus;
	}
	
	public void execute(Long id, Order orderModel) throws Exception {
	    log.info("Update Order with ID: {}", id);
		try {
			this.validStatus(orderModel);
			changeStatus.execute(orderModel,OrderStatus.PENDING_PAYMENT);
			this.updateOrder(id,orderModel);

//			return orderModel;
		} catch (Exception ex) {
			throw new Exception("falha ao atualizar status do pagamento");
		}
	
	//validStatus -> se for invalido ja vai pra expurgo algum fluxo de tratativa
	}

	private void changeStatusRules(Order orderModel) {
		//primeiroInsert na base de pagamento -> update order Status payment processing
		//Se status confirmado update status da ordem -> notificatodos -> processing
		
	}

	private void validStatus(Order orderModel) {
        //vamos fazer um validStatus -> cancelamento -> extorno
		//validStatus -> se for invalido ja vai pra expurgo algum fluxo de tratativa
	}
	
	
	private void updateOrder(Long id,Order orderModel) throws Exception {
		log.info("Creating new Order with ID: {}", orderModel.getOrderId());
		orderRepository.updateOrder(id, orderModel);
		log.info("SUCCESS! Order has been CREATED with ID: {}", orderModel.getOrderId());
	}

}
