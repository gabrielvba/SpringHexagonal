package com.acc.oss.storeReact.infrastructure.adapter.in.transform;

import com.acc.oss.storeReact.domain.model.Product;

public class TransformProductDtoToModel {

	public static Product execute(com.acc.oss.storeReact.infrastructure.adapter.in.rest.dto.Product product) {
		Product productModel = new Product();
		productModel.setPrecoUnitario(product.getPrecoUnitario());
		productModel.setProdutoId(product.getProdutoId());
		productModel.setQuantidade(product.getQuantidade());
		productModel.setName(product.getName());

		return productModel;
	}

}
