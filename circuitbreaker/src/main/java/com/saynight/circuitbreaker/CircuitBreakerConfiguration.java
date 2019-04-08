package com.saynight.circuitbreaker;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saynight.circuitbreaker.constants.CRConstants;
import com.saynight.common.exception.BusinessException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.consumer.EventConsumerRegistry;

@Configuration
public class CircuitBreakerConfiguration {
	
	@Resource
	private CircuitBreakerFactory circuitBreakerFactory;
	@Resource
	private EventConsumerRegistry<CircuitBreakerEvent> eventConsumerRegistry;
	
	
	@Bean(name = CRConstants.CIRCUITBREAKERAOP)
	public CircuitBreaker circuitBreaker() {
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create(CRConstants.CIRCUITBREAKERAOP, 10, 1 * 1000, 10, 10, BusinessException.class);
		circuitBreaker.getEventPublisher().onEvent(eventConsumerRegistry.createEventConsumer(CRConstants.CIRCUITBREAKERAOP, 100));
		return circuitBreaker;
	}

}
