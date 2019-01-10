package com.kundu.customerstatement;

import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Starter point of the application.
 * 
 * This class could be run as a Java Application to be able to start the application. Alternatively it could be use maven targets, described in the
 * README file.
 * 
 * @author ukundukan
 * 
 */
@SpringBootApplication
@EnableAsync
public class CustomerStatementApplication {

	/**
	 * Starter point of application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(CustomerStatementApplication.class, args);
	}

	/**
	 * Bean configuration for Async processes
	 * 
	 * @return
	 */
	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Async-");
		executor.initialize();
		return executor;
	}

}
