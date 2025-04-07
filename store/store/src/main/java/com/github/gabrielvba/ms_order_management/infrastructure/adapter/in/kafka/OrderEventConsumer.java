package com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.kafka;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class OrderEventConsumer {

//	@Autowired
//	CreateOrderUserCase createOrderUserCase;
//
//    @KafkaListener(topics = "pedido.criado", groupId = "pedido-group")
//    public void processarPedido(OrderEvent evento, Acknowledgment acknowledgment) throws Exception {
//        log.info("OrderEvent Received: {}", evento);
//
//    	com.acc.oss.store.domain.model.Order orderModel = null;
//        try {
//        	orderModel = TransformOrderEventToModel.execute(evento);
//        	createOrderUserCase.execute(orderModel);
//        } finally {
//            if (evento != null)
//                acknowledgment.acknowledge();
//        }
//        
//    }
}