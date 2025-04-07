package com.github.gabrielvba.ms_order_management.application.port.out;

import java.util.List;

import com.github.gabrielvba.ms_order_management.domain.dto.ProductDTO;

public interface ProductServicePort {

	List<ProductDTO> getProducts(List<Long> externalProductIdList);

}
