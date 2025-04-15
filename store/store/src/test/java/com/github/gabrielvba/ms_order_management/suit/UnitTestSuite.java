package com.github.gabrielvba.ms_order_management.suit;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.github.gabrielvba.ms_order_management.transformer.TransformOrderDtoToModelTest;

@Suite
@SelectClasses({
	TransformOrderDtoToModelTest.class,
})
public class UnitTestSuite {

}
