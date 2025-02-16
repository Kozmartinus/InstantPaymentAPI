package com.paymentapi;

import org.springframework.boot.SpringApplication;

public class TestPaymentapiApplication {

	public static void main(String[] args) {
		SpringApplication.from(PaymentApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
