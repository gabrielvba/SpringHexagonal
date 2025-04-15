package com.github.gabrielvba.ms_product_service.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private BigDecimal unitPrice;
    private String barcode;
    private BigDecimal weight;
    private String dimensions;
    private String category;
    private String brand;
    private String manufactureDate;
    private String expiryDate;
    private String supplier;
//    private List<String> images;
//    private List<String> reviews;

}
