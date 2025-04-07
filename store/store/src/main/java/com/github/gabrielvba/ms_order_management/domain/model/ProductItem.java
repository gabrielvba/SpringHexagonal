package com.github.gabrielvba.ms_order_management.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem extends BaseEntity {
	private Long id;
    private Long externalProductId;
    private String name;
    private BigDecimal unitPrice;
    private int quantity;
    private String promotionCode;
}
