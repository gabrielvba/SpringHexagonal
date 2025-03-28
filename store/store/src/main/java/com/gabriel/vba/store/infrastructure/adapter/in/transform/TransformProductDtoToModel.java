package com.gabriel.vba.store.infrastructure.adapter.in.transform;

import com.gabriel.vba.store.domain.model.Product;

public class TransformProductDtoToModel {

	public static Product execute(com.gabriel.vba.store.infrastructure.adapter.in.rest.dto.Product product) {
		Product productModel = new Product();
		productModel.setPrecoUnitario(product.getPrecoUnitario());
		productModel.setProdutoId(product.getProdutoId());
		productModel.setQuantidade(product.getQuantidade());
		productModel.setName(product.getName());

		return productModel;
	}

}
