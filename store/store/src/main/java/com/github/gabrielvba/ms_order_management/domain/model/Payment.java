package com.github.gabrielvba.ms_order_management.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long paymentId;
    @OneToOne(mappedBy = "payment")
	Order order;
	Long external_transaction_id;
	String paymentMethod;
    @Enumerated(EnumType.STRING)
	PaymentStatus status;
	LocalDateTime completionDate;
	BigDecimal totalAmount;

}
