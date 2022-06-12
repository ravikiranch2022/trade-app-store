package com.tradeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradeAppStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeAppStoreApplication.class, args);
	}

}
