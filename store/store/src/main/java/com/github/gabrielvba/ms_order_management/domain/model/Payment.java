package com.github.gabrielvba.ms_order_management.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
public class Payment extends BaseEntity {

	Long paymentId;
	Long orderId;
	Long external_transaction_id;
	String paymentMethod;
	PaymentStatus status;
	LocalDateTime  completionDate;
	BigDecimal totalAmount;
	
}
