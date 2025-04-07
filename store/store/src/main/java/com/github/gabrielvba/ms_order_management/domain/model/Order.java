package com.github.gabrielvba.ms_order_management.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
public class Order extends BaseEntity {

//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
    private Long customerId;
    private List<ProductItem> items;
    private String deliveryAddress;
    private String promotionCode;
    private BigDecimal totalAmount;
//    @Enumerated(EnumType.STRING) // <- aqui está o segredo
    private OrderStatus status; 
    private Payment payment;
    private Long shipmentId;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime  completionDate;

}
