package com.gabriel.vba.store.infrastructure.adapter.in.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.vba.store.application.port.in.CreateOrderUserCase;
import com.gabriel.vba.store.infrastructure.adapter.in.rest.dto.Product;
import com.gabriel.vba.store.infrastructure.adapter.in.transform.TransformProductDtoToModel;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProductController {

	@Autowired
	CreateOrderUserCase createOrderUserCase;
	
    @PostMapping
    public ResponseEntity<Object> criarPedido(@RequestBody Product product) {
        log.info("create Product: {}", product);

    	com.gabriel.vba.store.domain.model.Product productModel = null;
        try {
        	productModel = TransformProductDtoToModel.execute(product);
        } catch (Exception ex) {
        	return ResponseEntity.badRequest().body(ex.getMessage());
        }
        
    	com.gabriel.vba.store.domain.model.Product newProduct = createOrderUserCase.criarProduto(productModel);
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<com.gabriel.vba.store.domain.model.Product>> listarPedidos() {
        log.info("list products");

        List<com.gabriel.vba.store.domain.model.Product> products = createOrderUserCase.listarProdutos();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarPedido(@PathVariable Long id, @RequestBody Product product) {
        log.info("update Product: {}", product);

    	com.gabriel.vba.store.domain.model.Product productModel = null;
        try {
        	productModel = TransformProductDtoToModel.execute(product);
        } catch (Exception ex) {
        	return ResponseEntity.badRequest().body(ex.getMessage());
        }
        
         createOrderUserCase.atualizarProduto(id, productModel);
         return ResponseEntity.ok().body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        if (createOrderUserCase.deletarProduto(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
//    @GetMapping("/{id}")
//    public ResponseEntity<PedidoResponse> buscarPedidoPorId(@PathVariable Long id) {
//        return createOrderUserCase.buscarProdutoPorId(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
}
