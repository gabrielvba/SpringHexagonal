package com.github.gabrielvba.ms_order_management_react.infrastructure.adapter.out.implementation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gabrielvba.ms_order_management_react.domain.model.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

}
