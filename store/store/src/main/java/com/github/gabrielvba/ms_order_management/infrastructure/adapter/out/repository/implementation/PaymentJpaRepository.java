package com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository.implementation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.gabrielvba.ms_order_management.domain.model.Payment;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

}
