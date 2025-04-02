package com.gabriel.vba.storeReact.infrastructure.adapter.out.implementation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.vba.storeReact.domain.model.Product;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

}
