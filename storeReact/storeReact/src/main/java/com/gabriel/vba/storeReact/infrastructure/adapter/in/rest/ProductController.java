package com.gabriel.vba.storeReact.infrastructure.adapter.in.rest;

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

import com.gabriel.vba.storeReact.application.port.in.CrudProductUserCase;
import com.gabriel.vba.storeReact.infrastructure.adapter.in.rest.dto.Product;
import com.gabriel.vba.storeReact.infrastructure.adapter.in.transform.TransformProductDtoToModel;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProductController {

	@Autowired
	CrudProductUserCase productUserCase;

	@PostMapping
	public Mono<ResponseEntity<com.gabriel.vba.storeReact.domain.model.Product>> criarProduto(@RequestBody Product product) {
	    log.info("Create Product: {}", product);

	    return Mono.just(product)
	            .map(TransformProductDtoToModel::execute)
	            .flatMap(productModel -> productUserCase.criarProduto(productModel)
	                    .map(newProduct -> ResponseEntity.status(HttpStatus.CREATED).body(newProduct)))
	            .onErrorResume(ex -> {
	                log.error("Erro ao criar produto", ex);
	                return Mono.just(ResponseEntity.badRequest().build());
	            });
	}

	@GetMapping
	public Flux<com.gabriel.vba.storeReact.domain.model.Product> listarProdutos() {
		log.info("List products");
		return productUserCase.listarProdutos();
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<com.gabriel.vba.storeReact.domain.model.Product>> atualizarProduto(@PathVariable Long id, @RequestBody Product product) {
	    log.info("Update Product: {}", product);

	    return Mono.fromCallable(() -> TransformProductDtoToModel.execute(product))
	            .flatMap(productModel -> productUserCase.atualizarProduto(id, productModel)
	                    .map(updatedProduct -> ResponseEntity.ok().body(updatedProduct)))
	            .onErrorResume(ex -> {
	                log.error("Erro ao atualizar produto", ex);
	                return Mono.just(ResponseEntity.badRequest().build());
	            });
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Object>> deletarProduto(@PathVariable Long id) {
	    return productUserCase.deletarProduto(id)
	            .then(Mono.just(ResponseEntity.noContent().build())) // Retorna 204 após a execução bem-sucedida
	            .onErrorResume(ex -> {
	                log.error("Erro ao deletar produto", ex);
	                return Mono.just(ResponseEntity.notFound().build()); // Retorna 404 se houver erro
	            });
	}

//    @GetMapping("/{id}")
//    public ResponseEntity<PedidoResponse> buscarPedidoPorId(@PathVariable Long id) {
//        return createOrderUserCase.buscarProdutoPorId(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
}
