package com.github.gabrielvba.ms_order_management.suit;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.github.gabrielvba.ms_order_management.OrderManagementE2E;

@Suite
@SelectClasses({
	OrderManagementE2E.class,
	IntegrationTestSuite.class,
	UnitTestSuite.class
})
public class AllTestsSuite {

}
