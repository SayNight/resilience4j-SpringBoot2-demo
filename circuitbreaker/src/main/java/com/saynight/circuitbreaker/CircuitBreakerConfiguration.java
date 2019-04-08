package com.saynight.circuitbreaker;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saynight.circuitbreaker.constants.CRConstants;
import com.saynight.common.exception.BusinessException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

@Configuration
public class CircuitBreakerConfiguration {
	
	@Resource
	private CircuitBreakerFactory circuitBreakerFactory;
	
	@Bean(name = CRConstants.CIRCUITBREAKERAOP)
	public CircuitBreaker circuitBreaker() {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create(CRConstants.CIRCUITBREAKERAOP, 10, 1 * 1000, 10, 10, BusinessException.class);
		return circuitBreaker;
	}

}
