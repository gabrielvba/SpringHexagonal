package com.github.gabrielvba.ms_product_service.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductDTO(Long productId, String name, String description, BigDecimal unitPrice,
        String barcode, BigDecimal weight, String dimensions, String category,
        String brand, String manufactureDate, String expiryDate, 
//        List<String> images, List<String> reviews,
        String supplier
        ) {
}
