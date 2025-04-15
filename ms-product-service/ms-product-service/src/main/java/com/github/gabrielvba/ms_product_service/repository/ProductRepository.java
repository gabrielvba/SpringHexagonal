package com.github.gabrielvba.ms_product_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gabrielvba.ms_product_service.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductIdIn(List<Long> productId);


}