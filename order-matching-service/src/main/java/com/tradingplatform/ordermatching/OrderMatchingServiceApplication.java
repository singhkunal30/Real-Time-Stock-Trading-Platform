package com.tradingplatform.ordermatching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.tradingplatform.common", "com.tradingplatform.ordermatching" })
public class OrderMatchingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderMatchingServiceApplication.class, args);
	}

}
