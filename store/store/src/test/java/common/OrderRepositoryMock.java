package common;

import java.util.List;

import com.github.gabrielvba.ms_order_management.application.port.out.OrderRepositoryPort;
import com.github.gabrielvba.ms_order_management.domain.model.Order;

public class OrderRepositoryMock implements OrderRepositoryPort{

	@Override
	public Order getOrder(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order saveOrder(Order orderModel) {
		orderModel.getItems().forEach(e -> e.setId(e.getExternalProductId()));
		orderModel.setOrderId(1l);
		return orderModel;
	}

	@Override
	public Order updateOrder(Long id, Order orderModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrder(Long id) {
		// TODO Auto-generated method stub
		
	}

}
