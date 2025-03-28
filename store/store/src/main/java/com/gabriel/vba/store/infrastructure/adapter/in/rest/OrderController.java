package com.gabriel.vba.store.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.vba.store.application.port.in.CreateOrderUserCase;
import com.gabriel.vba.store.infrastructure.adapter.in.rest.dto.Order;
import com.gabriel.vba.store.infrastructure.adapter.in.transform.TransformOrderDtoToModel;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class OrderController {

	@Autowired
	CreateOrderUserCase createOrderUserCase;

    @PostMapping
    public ResponseEntity<Object> criarPedido(@RequestBody Order order) throws Exception {
        log.info("Order Received: {}", order);

    	com.gabriel.vba.store.domain.model.Order orderModel = null;
        try {
        	orderModel = TransformOrderDtoToModel.execute(order);
        } catch (Exception ex) {
        	return ResponseEntity.badRequest().body(ex.getMessage());
        }
        
        createOrderUserCase.execute(orderModel);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<List<com.gabriel.vba.store.domain.model.Order>> listarPedidos() {
        List<com.gabriel.vba.store.domain.model.Order> pedidos = createOrderUserCase.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }
    
}