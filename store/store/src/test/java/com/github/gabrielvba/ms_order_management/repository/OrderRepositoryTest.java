package com.github.gabrielvba.ms_order_management.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; // <-- aqui!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.github.gabrielvba.ms_order_management.domain.model.Order;
import com.github.gabrielvba.ms_order_management.domain.model.OrderStatus;
import com.github.gabrielvba.ms_order_management.domain.model.Payment;
import com.github.gabrielvba.ms_order_management.domain.model.PaymentStatus;
import com.github.gabrielvba.ms_order_management.domain.model.ProductItem;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository.OrderRepository;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository.implementation.OrderJpaRepository;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository.implementation.PaymentJpaRepository;
import com.github.gabrielvba.ms_order_management.infrastructure.adapter.out.repository.implementation.ProductItemJpaRepository;

@DataJpaTest
@ActiveProfiles("test")
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private ProductItemJpaRepository productItemJpaRepository;

    @Autowired
    private PaymentJpaRepository paymentJpaRepository;

    private OrderRepository orderRepository;

    @BeforeEach
    public void setup() {
        this.orderRepository = new OrderRepository(orderJpaRepository, productItemJpaRepository, paymentJpaRepository);
    }

    @Test
    public void saveOrder_WithValidOrder_ShouldPersistOrder() {
        Order order = OrderFactory.createExampleOrder();

        Order savedOrder = orderRepository.saveOrder(order);

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getOrderId()).isNotNull();
        assertThat(savedOrder.getItems()).isNotEmpty();
        assertThat(savedOrder.getPayment()).isNotNull();
    }

    @Test
    public void getOrder_WithExistingId_ShouldReturnOrder() {
        Order order = OrderFactory.createExampleOrder();
        Order persisted = testEntityManager.persistFlushFind(order);

        Order found = orderRepository.getOrder(persisted.getOrderId());

        assertThat(found).isNotNull();
        assertThat(found.getOrderId()).isEqualTo(persisted.getOrderId());
    }

    @Test
    public void deleteOrder_ShouldRemoveFromDatabase() {
        Order order = OrderFactory.createExampleOrder();
        Order persisted = testEntityManager.persistFlushFind(order);

        orderRepository.deleteOrder(persisted.getOrderId());

        Order found = testEntityManager.find(Order.class, persisted.getOrderId());
        assertThat(found).isNull();
    }

    public class OrderFactory {

        public static Order createExampleOrder() {
            Order order = new Order();
            order.setCustomerId(67890L);
            order.setDeliveryAddress("1234 Elm Street, Springfield, IL");
            order.setPromotionCode("DISCOUNT10");
            order.setTotalAmount(new BigDecimal("251.00"));
            order.setStatus(OrderStatus.PENDING_PAYMENT); // ou outro status conforme enum definido
            order.setEstimatedDeliveryDate(LocalDateTime.now().plusDays(5));
            order.setCompletionDate(null); // ainda não concluído

            ProductItem item = new ProductItem();
            item.setExternalProductId(1L);
            item.setName("Product A");
            item.setUnitPrice(new BigDecimal("100.50"));
            item.setQuantity(2);
            item.setPromotionCode("DISCOUNT10");
            item.setOrder(order); // necessário para manter a referência bidirecional

            order.setItems(List.of(item));

            // Criando pagamento
            Payment payment = new Payment();
            payment.setExternal_transaction_id(123456L);
            payment.setPaymentMethod("CREDIT_CARD");
            payment.setStatus(PaymentStatus.PENDING); // ou PAID, REJECTED, etc.
            payment.setCompletionDate(null); // ainda não finalizado
            payment.setTotalAmount(order.getTotalAmount());
            payment.setOrder(order); // manter bidirecionalidade

            // Associar payment ao order
            order.setPayment(payment);
            
            return order;
        }
    }
//  @Test
//  public void createPlanet_WithValidData_ReturnsPlanet() {
//	String orderJsonRequest = jsonReader.readJsonAsString("mock/orderDtoStart.json");
//
//    Order planet = OrderRepository.saveOrder(orderJsonRequest);
//    Planet planet = testEntityManager.persistFlushFind(PLANET);
//
//    Order sut = testEntityManager.find(Order.class, planet.getId());
//
//    assertThat(sut).isNotNull();
//    assertThat(sut.getName()).isEqualTo(Order.getName());
//    assertThat(sut.getClimate()).isEqualTo(Order.getClimate());
//    assertThat(sut.getTerrain()).isEqualTo(Order.getTerrain());
//  }

//  @ParameterizedTest
//  @MethodSource("providesInvalidPlanets")
//  public void createPlanet_WithInvalidData_ThrowsException(Order planet) {
//    assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);
//  }
//
//  private static Stream<Arguments> providesInvalidPlanets() {
//    return Stream.of(
//        Arguments.of(new Order(null, "climate", "terrain")),
//        Arguments.of(new Order("name", null, "terrain")),
//        Arguments.of(new Order("name", "climate", null)),
//        Arguments.of(new Order(null, null, "terrain")),
//        Arguments.of(new Order(null, "climate", null)),
//        Arguments.of(new Order("name", null, null)),
//        Arguments.of(new Order(null, null, null)),
//        Arguments.of(new Order("", "climate", "terrain")),
//        Arguments.of(new Order("name", "", "terrain")),
//        Arguments.of(new Order("name", "climate", "")),
//        Arguments.of(new Order("", "", "terrain")),
//        Arguments.of(new Order("", "climate", "")),
//        Arguments.of(new Order("name", "", "")),
//        Arguments.of(new Order("", "", "")));
//  }
//
//  @Test
//  public void getPlanet_ByExistingId_ReturnsPlanet() {
//    Planet planet = testEntityManager.persistFlushFind(PLANET);
//
//    Optional<Planet> planetOpt = planetRepository.findById(planet.getId());
//
//    assertThat(planetOpt).isNotEmpty();
//    assertThat(planetOpt.get()).isEqualTo(planet);
//  }

}