package com.github.gabrielvba.ms_order_management.suit;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.github.gabrielvba.ms_order_management.controller.OrderControllerTest;
import com.github.gabrielvba.ms_order_management.controller.OrderControllerSpringMvcTest;
import com.github.gabrielvba.ms_order_management.controller.OrderControllerWithMockitoTest;
import com.github.gabrielvba.ms_order_management.repository.OrderRepositoryTest;

@Suite
@SelectClasses({
	OrderControllerTest.class,
    OrderControllerWithMockitoTest.class,
    OrderControllerSpringMvcTest.class,
    OrderRepositoryTest.class
})
public class IntegrationTestSuite {

}
