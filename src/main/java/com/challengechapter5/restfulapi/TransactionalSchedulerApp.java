package com.challengechapter5.restfulapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class TransactionalSchedulerApp {

	public static void main(String[] args) {
		SpringApplication.run(TransactionalSchedulerApp.class, args);
	}

}