package com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.OrderStatus;
import com.github.gabrielvba.ms_order_management.domain.model.Payment;
import com.github.gabrielvba.ms_order_management.domain.model.ProductItem;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository.implementation.OrderJpaRepository;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository.implementation.PaymentJpaRepository;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository.implementation.ProductItemJpaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class OrderRepository implements OrderRepositoryPort {

	private final OrderJpaRepository orderJpaRepository;
	private final ProductItemJpaRepository productJpaRepository;
	private final PaymentJpaRepository paymentJpaRepository;
	
	public OrderRepository(OrderJpaRepository orderJpaRepository, ProductItemJpaRepository productJpaRepository,
			PaymentJpaRepository paymentJpaRepository) {
		super();
		this.orderJpaRepository = orderJpaRepository;
		this.productJpaRepository = productJpaRepository;
		this.paymentJpaRepository = paymentJpaRepository;
	}

	@Override
	@Transactional
	public Order saveOrder(Order orderModel) {
	    log.info("Saving new order");
	    for (ProductItem item : orderModel.getItems()) {
	        item.setOrder(orderModel); // Certifique-se de que cada ProductItem tem a referência para a Order
	    }
	    Order saved = orderJpaRepository.save(orderModel);
	    orderJpaRepository.flush(); // Garante que o flush seja executado
	    return saved;

	}

	@Override
	public List<Order> getAllOrders() {
	    log.info("Find all orders");
	    return orderJpaRepository.findAll();
	}

	@Override
	public void deleteOrder(Long id) {
	    log.info("Delete order with id: {}", id);
	    orderJpaRepository.deleteById(id);
	}

	@Override
	public Order updateOrder(Long id, Order updatedOrder) {
		 log.info("Atualizando pedido com ID: {}", id);

		    // Buscar o pedido existente
		    Order existingOrder = orderJpaRepository.findById(id)
		        .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

		    // Verificar se o pedido já foi concluído
		    if (existingOrder.getStatus() == OrderStatus.COMPLETED) {
		        throw new IllegalStateException("Pedidos concluídos não podem ser alterados.");
		    }

		    // Atualizar campos simples
		    existingOrder.setCustomerId(updatedOrder.getCustomerId());
		    existingOrder.setDeliveryAddress(updatedOrder.getDeliveryAddress());
		    existingOrder.setPromotionCode(updatedOrder.getPromotionCode());
		    existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
		    existingOrder.setStatus(updatedOrder.getStatus());
		    existingOrder.setShipmentId(updatedOrder.getShipmentId());
		    existingOrder.setEstimatedDeliveryDate(updatedOrder.getEstimatedDeliveryDate());
		    existingOrder.setCompletionDate(updatedOrder.getCompletionDate());

		    // Atualizar ou manter o Payment
		    Payment updatedPayment = updatedOrder.getPayment();
		    if (updatedPayment != null) {
		        Payment paymentToSet = atualizarOuCriarPagamento(updatedPayment, existingOrder);
		        existingOrder.setPayment(paymentToSet);
		    }
			return orderJpaRepository.save(existingOrder) ;

			
//		não acho que os items do pedido devam poder ser atualizados	    
//	    // Atualiza o relacionamento OneToMany (ProductItem)
//	    List<ProductItem> newItems = updatedOrder.getItems();
//	    if (newItems != null) {
//	        // Remove os itens antigos
//	        existingOrder.getItems().clear();
//
//	        // Adiciona os novos itens
//	        for (ProductItem item : newItems) {
//	            item.setOrder(existingOrder); // Estabelece a relação bidirecional
//	            existingOrder.getItems().add(item);
//	        }
//	    }

	}
	
	private Payment atualizarOuCriarPagamento(Payment updatedPayment, Order existingOrder) {
		if (updatedPayment.getPaymentId() != null) {
			return paymentJpaRepository.findById(updatedPayment.getPaymentId())
			    .map(existing -> {
			        existing.setPaymentMethod(updatedPayment.getPaymentMethod());
			        existing.setStatus(updatedPayment.getStatus());
			        existing.setCompletionDate(updatedPayment.getCompletionDate());
			        existing.setTotalAmount(updatedPayment.getTotalAmount());
			        existing.setExternal_transaction_id(updatedPayment.getExternal_transaction_id());
			        return existing;
			    })
			    .orElseGet(() -> {
			        updatedPayment.setOrder(existingOrder);
			        return updatedPayment;
			    });
		} else {
			updatedPayment.setOrder(existingOrder);
			return updatedPayment;
		}
	}

	@Override
	public Order getOrder(long id) {
	    log.info("Buscando pedido com ID {}", id);
	    return orderJpaRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + id + " não encontrado"));
	}
	
}
