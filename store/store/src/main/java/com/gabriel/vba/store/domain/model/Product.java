package com.gabriel.vba.store.domain.model;

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
public class Product {

	   private Long produtoId;
	   private int quantidade;
	   private BigDecimal precoUnitario;
	   private String name;

}
