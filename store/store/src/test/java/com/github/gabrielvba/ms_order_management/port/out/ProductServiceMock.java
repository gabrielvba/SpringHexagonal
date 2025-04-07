package com.github.gabrielvba.ms_order_management.port.out;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.gabrielvba.ms_order_management.application.port.out.ProductServicePort;
import com.github.gabrielvba.ms_order_management.domain.dto.ProductDTO;

public class ProductServiceMock implements ProductServicePort {

	Map<Long, ProductDTO> products = new HashMap<>(Map.of(
	    		1l, new ProductDTO(1l, new BigDecimal("29.99"), "Product 1", "Product Description"), 
	    		2l, new ProductDTO(2l, new BigDecimal("29.99"), "Product 2", "Product Description"), 
	    		3l, new ProductDTO(3L, new BigDecimal("29.99"), "Product 3", "Product Description"), 
	    		4l, new ProductDTO(4L, new BigDecimal("29.99"), "Product 4", "Product Description"), 
	    		5l, new ProductDTO(5L, new BigDecimal("29.99"), "Product 5", "Product Description"),
	    		6l, new ProductDTO(6L, new BigDecimal("29.99"), "Product 6", "Product Description")
	    )
	);	
	@Override
	public List<ProductDTO> getProducts(List<Long> productIds) {
		return productIds.stream()
				.map(id -> products.get(id))
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

}
