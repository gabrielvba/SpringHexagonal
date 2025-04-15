package com.github.gabrielvba.ms_order_management.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "orders")
public class Order extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;
    private Long customerId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //orphanRemoval = true
    private List<ProductItem> items;
    private String deliveryAddress;
    private String promotionCode;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING) 
    private OrderStatus status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", nullable = true)
    private Payment payment;
    private Long shipmentId;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime  completionDate;

}
