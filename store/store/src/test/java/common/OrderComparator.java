package common;

import org.junit.jupiter.api.Assertions;

import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.Payment;
import com.github.gabrielvba.ms_order_management.domain.model.ProductItem;

public class OrderComparator {

	public void compareOrderModel(Order transformedOrderModel, Order orderModelOut) {
		// Comparando o orderId
		Assertions.assertEquals(transformedOrderModel.getOrderId(), orderModelOut.getOrderId());

		// Comparando o customerId
		Assertions.assertEquals(transformedOrderModel.getCustomerId(), orderModelOut.getCustomerId());

		// Comparando os itens (Produto)
		Assertions.assertEquals(transformedOrderModel.getItems().size(), orderModelOut.getItems().size());

		if(orderModelOut.getItems() != null && orderModelOut.getItems().get(0) != null) compareOrderItems(transformedOrderModel.getItems().get(0), orderModelOut.getItems().get(0));
		if(orderModelOut.getItems() != null && orderModelOut.getItems().get(1) != null) compareOrderItems(transformedOrderModel.getItems().get(1), orderModelOut.getItems().get(1));

		// Comparando o deliveryAddress
		Assertions.assertEquals(transformedOrderModel.getDeliveryAddress(), orderModelOut.getDeliveryAddress());

		// Comparando o promotionCode
		Assertions.assertEquals(transformedOrderModel.getPromotionCode(), orderModelOut.getPromotionCode());

		// Comparando o totalAmount
		Assertions.assertEquals(transformedOrderModel.getTotalAmount(), orderModelOut.getTotalAmount());

		// Comparando o status do pedido (OrderStatus)
		Assertions.assertEquals(transformedOrderModel.getStatus(), orderModelOut.getStatus());

		if(orderModelOut.getPayment() != null) compareOrderPayment(transformedOrderModel.getPayment(), orderModelOut.getPayment());

		// Comparando o shipmentId
		Assertions.assertEquals(transformedOrderModel.getShipmentId(), orderModelOut.getShipmentId());

		// Comparando o estimatedDeliveryDate
		Assertions.assertEquals(transformedOrderModel.getEstimatedDeliveryDate(),
				orderModelOut.getEstimatedDeliveryDate());

		// Comparando o completionDate
		Assertions.assertEquals(transformedOrderModel.getCompletionDate(), orderModelOut.getCompletionDate());
	}

	private void compareOrderItems(ProductItem item1, ProductItem item2) {
		Assertions.assertEquals(item1.getId(), item2.getId(), "ProductItem IDs do not match");
		Assertions.assertEquals(item1.getExternalProductId(), item2.getExternalProductId(),
				"ExternalProductIds do not match");
		Assertions.assertEquals(item1.getName(), item2.getName(), "Product names do not match");
		Assertions.assertEquals(item1.getUnitPrice(), item2.getUnitPrice(), "Unit prices do not match");
		Assertions.assertEquals(item1.getQuantity(), item2.getQuantity(), "Quantities do not match");
		Assertions.assertEquals(item1.getPromotionCode(), item2.getPromotionCode(), "Promotion codes do not match");
	}

	private void compareOrderPayment(Payment payment1, Payment payment2) {
		Assertions.assertEquals(payment1.getExternal_transaction_id(), payment2.getExternal_transaction_id(),
				"External transaction IDs do not match");
		Assertions.assertEquals(payment1.getStatus(), payment2.getStatus(), "Payment statuses do not match");
		Assertions.assertEquals(payment1.getPaymentId(), payment2.getPaymentId(), "Payment IDs do not match");
		Assertions.assertEquals(payment1.getPaymentMethod(), payment2.getPaymentMethod(),
				"Payment methods do not match");
//		Assertions.assertEquals(payment1.getOrderId(), payment2.getOrderId(), "Order IDs do not match");
		Assertions.assertEquals(payment1.getCompletionDate(), payment2.getCompletionDate(),
				"Completion dates do not match");
		Assertions.assertEquals(payment1.getTotalAmount(), payment2.getTotalAmount(), "Total amounts do not match");
	}

}
