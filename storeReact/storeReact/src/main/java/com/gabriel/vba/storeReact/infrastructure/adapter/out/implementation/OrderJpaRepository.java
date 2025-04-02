package com.gabriel.vba.storeReact.infrastructure.adapter.out.implementation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gabriel.vba.storeReact.domain.model.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

	@Query("SELECT o FROM Order o LEFT JOIN FETCH o.itens")
	List<Order> findAllWithProducts();
}
