package com.github.gabrielvba.ms_order_management_react.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "orderProducts") // OU outro nome válido
public class ProductItem {

	    private Long id;
	    private Product product;
	    private int quantity;
	    String promotionCode;

}
