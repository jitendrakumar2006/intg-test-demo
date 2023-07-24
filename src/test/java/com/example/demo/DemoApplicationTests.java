package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@EnabledIfSystemProperty(named = "it.tests", matches = "true")
class DemoApplicationTests {

	//@Autowired
	//MathOps mathOps;

	@Value("${int1}")
	int int1;

	@Value("${int2}")
	int int2;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		MathOps mathOpsBean = applicationContext.getBean(MathOps.class);
		System.out.println(mathOpsBean);
		System.out.println("env var int1 value :" + int1);
		System.out.println("env var int2 value :" + int2);
		int sum = mathOpsBean.add(int1, int2);
		System.out.println("sum of " + int1 + " and " + int2 + " is : " + sum);
	}

}
