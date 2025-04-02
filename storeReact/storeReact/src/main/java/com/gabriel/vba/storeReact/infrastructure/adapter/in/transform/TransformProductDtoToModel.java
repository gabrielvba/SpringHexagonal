package com.gabriel.vba.storeReact.infrastructure.adapter.in.transform;

import com.gabriel.vba.storeReact.domain.model.Product;

public class TransformProductDtoToModel {

	public static Product execute(com.gabriel.vba.storeReact.infrastructure.adapter.in.rest.dto.Product product) {
		Product productModel = new Product();
		productModel.setPrecoUnitario(product.getPrecoUnitario());
		productModel.setProdutoId(product.getProdutoId());
		productModel.setQuantidade(product.getQuantidade());
		productModel.setName(product.getName());

		return productModel;
	}

}
