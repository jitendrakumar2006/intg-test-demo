package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

@AutoConfiguration
@SpringBootApplication
@ConfigurationProperties(prefix = DemoApplication.PREFIX)
public class DemoApplication {
	public static final String PREFIX = "demo";
	public DemoApplication() {

	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public MathOps getMathOps() {
		return new MathOps(extra);
	}

	@Nullable
	private Integer extra;

	@Nullable
	public Integer getExtra() {
		return extra;
	}

	public void setExtra(@Nullable Integer extra) {
		this.extra = extra;
	}
}
