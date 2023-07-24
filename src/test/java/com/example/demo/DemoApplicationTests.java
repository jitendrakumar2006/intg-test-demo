package com.example.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
@EnabledIfSystemProperty(named = "it.tests", matches = "true")
@TestPropertySource(locations="classpath:application-test.properties")
class DemoApplicationTests {

	//@Autowired
	//MathOps mathOps;

	@Value("${int1}")
	int int1;

	@Value("${int2}")
	int int2;

	@Autowired
	ApplicationContext applicationContext;

	public static final String PRIVATE_KEY_PATH = "privateKey";
	public static final String PRIVATE_KEY_CONTENT = "privateKeyContent";

	public static String privateKeyFileName = "privatekey.pem";
	static String userDir = System.getProperty("user.home");

	static String privateKeyFilePath = userDir + File.separator + privateKeyFileName;
	public static final String privateKeyContent = System.getProperty(PRIVATE_KEY_CONTENT) != null ? System.getProperty(PRIVATE_KEY_CONTENT) :
			System.getenv().get(PRIVATE_KEY_CONTENT);


	@BeforeAll
	static void beforeAll() throws Exception {
		System.out.println("privateKeyFilePath : " + privateKeyFilePath);
		FileUtils.createFile(privateKeyFilePath, privateKeyContent.replace("\\n", "\n"));
	}
	@Test
	void testIntegrations() throws Exception {
		File f = new File(privateKeyFilePath);
		System.out.println("****** private key file content started *******");
		System.out.println(new String(Files.readAllBytes(Paths.get(privateKeyFilePath)), StandardCharsets.UTF_8));
		System.out.println("****** private key file content ended *******");
		MathOps mathOpsBean = applicationContext.getBean(MathOps.class);
		System.out.println(mathOpsBean);
		System.out.println("env var int1 value :" + int1);
		System.out.println("env var int2 value :" + int2);
		int sum = mathOpsBean.add(int1, int2);
		System.out.println("sum of " + int1 + " and " + int2 + " is : " + sum);
	}

}
