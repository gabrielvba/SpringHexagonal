package com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gabrielvba.ms_order_management.domain.model.Order;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

//  @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items LEFT JOIN FETCH o.payment )
	@EntityGraph(attributePaths = { "items", "payment" })
	List<Order> findAll();

//    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items LEFT JOIN FETCH o.payment WHERE o.orderId = :id")
    @EntityGraph(attributePaths = {"items", "payment"})
    Optional<Order> findById(Long id);

}
