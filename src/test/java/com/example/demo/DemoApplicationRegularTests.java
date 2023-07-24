package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DemoApplicationRegularTests {

	//@Autowired
	//MathOps mathOps;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		MathOps mathOpsBean = applicationContext.getBean(MathOps.class);
		System.out.println(mathOpsBean);
		int sum = mathOpsBean.add(1, 2);
		System.out.println("sum of 1 and 2 is : " + sum);
	}

}
