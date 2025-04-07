package com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.gateway;

import java.util.List;

import org.springframework.stereotype.Component;

import com.github.gabrielvba.ms_order_management.application.port.out.ProductServicePort;
import com.github.gabrielvba.ms_order_management.domain.dto.ProductDTO;

@Component
public class ProductGateway implements ProductServicePort {

	@Override
	public List<ProductDTO> getProducts(List<Long> externalProductIdList) {
		return List.of(new ProductDTO(null, null, null, null));
	}

	
}
