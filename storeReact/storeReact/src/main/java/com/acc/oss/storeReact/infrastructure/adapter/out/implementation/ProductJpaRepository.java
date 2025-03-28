package com.acc.oss.storeReact.infrastructure.adapter.out.implementation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acc.oss.storeReact.domain.model.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

}
