package com.saynight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication(scanBasePackages = {"com.saynight"})
@EnableAutoConfiguration
@Slf4j
public class WebApplication {
	

	public static void main(String[] args) {
		log.info("---------------------WebApplication start-----------------------------");
		SpringApplication.run(WebApplication.class, args);
		log.info("---------------------WebApplication finished-----------------------------");
	}

}
