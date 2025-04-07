package com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.in.transform;

import com.github.gabrielvba.ms_order_management_react.domain.model.Product;

public class TransformProductDtoToModel {

	public static Product execute(com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.in.rest.dto.Product product) {
		Product productModel = new Product();
		productModel.setPrecoUnitario(product.getPrecoUnitario());
		productModel.setProdutoId(product.getProdutoId());
		productModel.setQuantidade(product.getQuantidade());
		productModel.setName(product.getName());

		return productModel;
	}

}
