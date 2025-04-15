//package com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.github.gabrielvba.ms_order_management.application.port.in.CancelOrderUserCase;
//import com.github.gabrielvba.ms_order_management.application.port.in.ConsultOrderUseCase;
//import com.github.gabrielvba.ms_order_management.application.port.in.CreateOrderUserCase;
//import com.github.gabrielvba.ms_order_management.application.port.in.OrderPickingUseCase;
//import com.github.gabrielvba.ms_order_management.application.port.in.OrderShippingUseCase;
//import com.github.gabrielvba.ms_order_management.application.port.in.PaymentProcessUseCase;
//import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order;
//import com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.transformer.TransformOrderDtoToModel;
//
//import lombok.extern.log4j.Log4j2;
//@Log4j2
//@RestController
//@RequestMapping("/orders2")
//public class OrderControllerSpringControll {
//
//    private final CreateOrderUserCase createOrderUserCase;
//    private final PaymentProcessUseCase paymentProcessUseCase;
//    private final OrderPickingUseCase orderPickingUseCase;
//    private final OrderShippingUseCase orderShippedUseCase;
//    private final CancelOrderUserCase cancelOrderUserCase;
//    private final ConsultOrderUseCase consultOrderUseCase;
//	private final TransformOrderDtoToModel transformOrderDtoToModel;
//
//    public OrderControllerSpringControll(CreateOrderUserCase createOrderUserCase,
//                           PaymentProcessUseCase paymentProcessUseCase,
//                           OrderPickingUseCase orderPickingUseCase,
//                           OrderShippingUseCase orderShippedUseCase,
//                           CancelOrderUserCase cancelOrderUserCase,
//                           ConsultOrderUseCase consultOrderUseCase) {
//        this.createOrderUserCase = createOrderUserCase;
//        this.paymentProcessUseCase = paymentProcessUseCase;
//        this.orderPickingUseCase = orderPickingUseCase;
//        this.orderShippedUseCase = orderShippedUseCase;
//        this.cancelOrderUserCase = cancelOrderUserCase;
//        this.consultOrderUseCase = consultOrderUseCase;
//		this.transformOrderDtoToModel = new TransformOrderDtoToModel();
//    }
//
//    @PostMapping(path = "/create")
//    public ResponseEntity<Object> createOrder(@RequestBody Order order) {
//        log.info("Order Received: {}", order);
//
//        try {
//            com.github.gabrielvba.ms_order_management.domain.model.Order orderModel = transformOrderDtoToModel.toModel(order);
//            var result = createOrderUserCase.execute(orderModel);
//            return ResponseEntity.ok(result);
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
//    }
//
//    @PostMapping(path = "/payment/{id}")
//    public ResponseEntity<Object> updateOrderPaymentProcess(@PathVariable Long id, @RequestBody Order order) {
//        log.info("Order Received: {}", order);
//
//        try {
//            com.github.gabrielvba.ms_order_management.domain.model.Order orderModel = transformOrderDtoToModel.toModel(order);
//            var result = paymentProcessUseCase.execute(id, orderModel);
//            return ResponseEntity.ok(result);
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
//    }
//
//    @PostMapping(path = "/picking/{id}")
//    public ResponseEntity<Object> updateOrderPicking(@PathVariable Long id, @RequestBody Order order) {
//        log.info("Order Received: {}", order);
//
//        try {
//            com.github.gabrielvba.ms_order_management.domain.model.Order orderModel = transformOrderDtoToModel.toModel(order);
//            orderPickingUseCase.execute(id, orderModel);
//            return ResponseEntity.ok().build();
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
//    }
//
//    @PostMapping(path = "/shipped/{id}")
//    public ResponseEntity<Object> updateOrderShipping(@PathVariable Long id, @RequestBody Order order) {
//        log.info("Order Received: {}", order);
//
//        try {
//            com.github.gabrielvba.ms_order_management.domain.model.Order orderModel = transformOrderDtoToModel.toModel(order);
//            orderShippedUseCase.execute(id, orderModel);
//            return ResponseEntity.ok().build();
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
//    }
//
//    @PostMapping(path = "/cancel/{id}")
//    public ResponseEntity<Object> cancelOrder(@PathVariable Long id, @RequestBody Order order) {
//        log.info("Order Received: {}", order);
//
//        try {
//            com.github.gabrielvba.ms_order_management.domain.model.Order orderModel = transformOrderDtoToModel.toModel(order);
//            cancelOrderUserCase.execute(id, orderModel);
//            return ResponseEntity.ok().build();
//        } catch (Exception ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<List<com.github.gabrielvba.ms_order_management.domain.model.Order>> listarPedidos() {
//        List<com.github.gabrielvba.ms_order_management.domain.model.Order> pedidos = consultOrderUseCase.execute();
//        return ResponseEntity.ok(pedidos);
//    }
//}