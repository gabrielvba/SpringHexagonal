package com.github.gabrielvba.ms_order_management_react.application.port.in;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management_react.application.port.out.InventoryService;
import com.github.gabrielvba.ms_order_management_react.application.port.out.OrderRepository;
import com.github.gabrielvba.ms_order_management_react.application.port.out.PaymentService;
import com.github.gabrielvba.ms_order_management_react.domain.model.Order;
import com.github.gabrielvba.ms_order_management_react.domain.model.ProductItem;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@NoArgsConstructor
public class CreateOrderUserCase {
	//ordem status CREATED
	@Autowired
	OrderRepository orderRepository;
	
    @Autowired
    InventoryService inventoryService;
    
    @Autowired
    PaymentService paymentService;
    
    public Mono<Order> execute(Order orderModel) {
        return validateProducts(orderModel)
//                .then(applyPromotions(orderModel))
                .then(inventoryService.validateInventory(orderModel))
                .then(orderRepository.saveOrder(orderModel))
                .flatMap(savedOrder -> {
//                    notificationService.sendOrderCreatedNotification(savedOrder);
                    return paymentService.sendPaymentRequest((Order) savedOrder);
                })
                .thenReturn(orderModel)
                .onErrorResume(ex -> {
                    log.error("Erro ao criar pedido", ex);
                    return Mono.error(new Exception("Falha ao criar um pedido", (Throwable) ex));
                });
    }



    private Mono<Void> validateProducts(Order orderModel) {
        return Flux.fromIterable(orderModel.getItens())
            .map(ProductItem::getProduct)
            .collectList()
            .flatMap(productList -> orderRepository.getProducts(productList)
                .collectList()
                .flatMap(products -> {
                    if (orderModel.getItens().size() > products.size()) {
                        return Mono.error(new Exception("Produto não existe"));
                    }
                    return Mono.empty();
                })
            );
    }
}
