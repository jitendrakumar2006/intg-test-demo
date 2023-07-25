package com.example.demo;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.*;
import com.oracle.bmc.ons.NotificationDataPlane;
import com.oracle.bmc.ons.NotificationDataPlaneClient;
import com.oracle.bmc.ons.model.SubscriptionSummary;
import com.oracle.bmc.ons.requests.ListSubscriptionsRequest;
import com.oracle.bmc.ons.responses.ListSubscriptionsResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;

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

	public static final String createPrivateFile = System.getProperty("createPrivateFile") != null ?
			System.getProperty("createPrivateFile") : (System.getenv().get("createPrivateFile") != null ?
			System.getenv().get("createPrivateFile") : "false");
	public static boolean isCreatePrivateFile = Boolean.parseBoolean(createPrivateFile);
	@Autowired
	ApplicationContext applicationContext;

	///public static final String PRIVATE_KEY_PATH = "privateKey";
	public static final String PRIVATE_KEY_CONTENT = "privateKeyContent";

	public static String privateKeyFileName = "privatekey.pem";
	static String userDir = System.getProperty("user.home");

	static String privateKeyFilePath = userDir + File.separator + privateKeyFileName;
	public static final String privateKeyContent = System.getProperty(PRIVATE_KEY_CONTENT) != null ? System.getProperty(PRIVATE_KEY_CONTENT) :
			System.getenv().get(PRIVATE_KEY_CONTENT);


	@BeforeAll
	static void beforeAll() throws Exception {
		if (isCreatePrivateFile) {
			FileUtils.createFile(privateKeyFilePath, privateKeyContent.replace("\\n", "\n"));
		}
	}
	@Test
	void testIntegrations() throws Exception {
		if (isCreatePrivateFile) {
			System.out.println("****** private key file content started *******");
			System.out.println(new String(Files.readAllBytes(Paths.get(privateKeyFilePath)), StandardCharsets.UTF_8));
			System.out.println("****** private key file content ended *******");
		}
		MathOps mathOpsBean = applicationContext.getBean(MathOps.class);
		System.out.println(mathOpsBean);
		System.out.println("env var int1 value :" + int1);
		System.out.println("env var int2 value :" + int2);
		int sum = mathOpsBean.add(int1, int2);
		System.out.println("sum of " + int1 + " and " + int2 + " is : " + sum);

		listSubscriptions("ocid1.onstopic.oc1.iad.aaaaaaaam6isoan6lzewkq7qckbum2q2fculvrya2kmrvyujduccycbdfahq",
				"ocid1.compartment.oc1..aaaaaaaatdogmj6wzyzau62nyh43nqpf4frgmx754ukyl76mvqu46tea6jla");
	}

	List<SubscriptionSummary> listSubscriptions(String topicId, String compartmentId) throws Exception {
		ListSubscriptionsRequest request = ListSubscriptionsRequest.builder().topicId(topicId).
				compartmentId(compartmentId).build();
		ListSubscriptionsResponse response = notificationDataPlaneClient(createCredentialsProvider()).listSubscriptions(request);
		System.out.println(" result subscriptions : " + response.getItems());
		System.out.println(" result subscriptions size : " + response.getItems().size());
		return response.getItems();
	}

	NotificationDataPlane notificationDataPlaneClient(BasicAuthenticationDetailsProvider adp) {
		NotificationDataPlane notificationDataPlaneClient = new NotificationDataPlaneClient(adp);
		return notificationDataPlaneClient;
	}


	public BasicAuthenticationDetailsProvider createCredentialsProvider() throws IOException {
		Supplier<InputStream> privateKeySupplier = null;
		if (isCreatePrivateFile) {
			privateKeySupplier = new SimplePrivateKeySupplier(privateKeyFilePath);
		} else {
			privateKeySupplier = new StringPrivateKeySupplier(privateKeyContent.replace("\\n", "\n"));
		}
		SimpleAuthenticationDetailsProvider.SimpleAuthenticationDetailsProviderBuilder builder =
				SimpleAuthenticationDetailsProvider.builder()
				.userId("ocid1.user.oc1..aaaaaaaasau2qkzhmtonim4dwoap3e5ijsu4fg6zojsbkgyf5cbjlqrxxiya")
				.tenantId("ocid1.tenancy.oc1..aaaaaaaabzkajrazgwhwndxtzl2ns235qgdr4d6x42ateayzwfo4vy4f5ada")
				.fingerprint("48:54:18:5f:db:f2:80:5b:91:76:74:06:97:55:cd:52")
						.privateKeySupplier(privateKeySupplier)
						.region(Region.fromRegionId("us-ashburn-1"));
		return builder.build();
	}

	@AfterAll
	static void afterAll() throws Exception {
		if (isCreatePrivateFile) {
			FileUtils.deleteFile(privateKeyFilePath);
		}
	}
}
