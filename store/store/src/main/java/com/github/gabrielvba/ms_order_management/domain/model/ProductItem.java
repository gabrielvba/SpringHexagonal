package com.github.gabrielvba.ms_order_management.domain.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "orderProducts")
public class ProductItem extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long externalProductId;
	private String name;
	private BigDecimal unitPrice;
	private int quantity;
	private String promotionCode;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) 
    @JsonIgnore
    private Order order;
}
