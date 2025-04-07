package com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.transformer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.gabrielvba.ms_order_management.domain.exception.InvalidStatusException;
import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.OrderStatus;
import com.github.gabrielvba.ms_order_management.domain.model.Payment;
import com.github.gabrielvba.ms_order_management.domain.model.PaymentStatus;
import com.github.gabrielvba.ms_order_management.domain.model.ProductItem;

public class TransformOrderDtoToModel {

	public static Order toModel(com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Order order) {
		Order orderModel = new Order();
		orderModel.setCustomerId(order.customerId());
		orderModel.setDeliveryAddress(order.deliveryAddress());
		orderModel.setPayment(toPaymentModel(order.payment())); 
		orderModel.setOrderId(order.orderId());
		orderModel.setItems(toProductItemModelList(order.items())); 
		orderModel.setStatus(stringToOrderStatus(order.status()));
		orderModel.setTotalAmount(order.totalAmount());
		orderModel.setPromotionCode(order.promotionCode());
		orderModel.setShipmentId(order.shipmentId());
		orderModel.setCompletionDate(order.completionDate());
		orderModel.setEstimatedDeliveryDate(order.estimatedDeliveryDate());

		return orderModel;
	}

    public static Payment toPaymentModel(com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.Payment dto) {
    	if(dto == null) {
    		return null;
    	}else {
    		Payment payment = new Payment();
    		payment.setPaymentId(dto.paymentId());
    		payment.setOrderId(dto.orderId());
    		payment.setExternal_transaction_id(dto.external_transaction_id());
    		payment.setPaymentMethod(dto.paymentMethod());
    		payment.setStatus(stringToPaymentStatus(dto.status()));
    		payment.setCompletionDate(dto.completionDate());
    		payment.setTotalAmount(dto.totalAmount());
    		return payment;
    	}
    }
    
    public static List<ProductItem> toProductItemModelList(List<com.github.gabrielvba.ms_order_management.infrastructure.adapter.in.rest.dto.ProductItem> productItemDtoList) {
        if (productItemDtoList == null) return Collections.emptyList();

        return productItemDtoList.stream()
                .map(dto -> {
                    ProductItem item = new ProductItem();
                    item.setId(dto.id());
                    item.setExternalProductId(dto.externalProductId());
                    item.setName(dto.name());
                    item.setUnitPrice(dto.unitPrice());
                    item.setQuantity(dto.quantity());
                    item.setPromotionCode(dto.promotionCode());
                    return item;
                })
                .collect(Collectors.toList());
    }
    
    public static PaymentStatus stringToPaymentStatus(String status) {
    	if (status == null) {
    		return null;
    	}
    	try {
            return PaymentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Payment Status inválido: " + status);
        }
    }
    
    public static OrderStatus stringToOrderStatus(String status) {
    	if (status == null) {
    		return null;
    	}
    	try {
            return OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidStatusException("Order Status inválido: " + status);
        }
    }
}
