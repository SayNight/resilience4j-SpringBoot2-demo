package com.saynight.web.circuitBreaker.service.impl;

import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saynight.circuitbreaker.CircuitBreakerFactory;
import com.saynight.circuitbreaker.constants.CRConstants;
import com.saynight.common.exception.BusinessException;
import com.saynight.web.circuitBreaker.connector.Connector;
import com.saynight.web.circuitBreaker.service.BusinessService;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.vavr.control.Try;
import reactor.core.publisher.Flux;

@Service
public class NoAopBusinessService implements BusinessService {

	@Resource
	private Connector noAopConnector;
	private CircuitBreaker circuitBreaker;
	@Resource
	private CircuitBreakerFactory circuitBreakerFactory;

	@PostConstruct
	public void init() {
		circuitBreaker = circuitBreakerFactory.create(CRConstants.CIRCUITBREAKERNOAOP, 10, 5 * 1000, 5, 10, BusinessException.class);
	}
	public String failure() {
		return CircuitBreaker.decorateSupplier(circuitBreaker, noAopConnector::failure).get();
	}

	public String success() {
		return CircuitBreaker.decorateSupplier(circuitBreaker, noAopConnector::success).get();
	}

	@Override
	public String ignore() {	
		return CircuitBreaker.decorateSupplier(circuitBreaker, noAopConnector::ignoreException).get();
	}

	@Override
	public Try<String> methodWithRecovery() {
		Supplier<String> backendFunction = CircuitBreaker.decorateSupplier(circuitBreaker,
				() -> noAopConnector.failure());
		return Try.ofSupplier(backendFunction).recover((throwable) -> recovery(throwable));
	}

	public Flux<String> methodWhichReturnsNoAopStream() {
		return noAopConnector.methodWhichReturnsAStream().transform(CircuitBreakerOperator.of(circuitBreaker));
	}

	private String recovery(Throwable throwable) {
		// Handle exception and invoke fallback
		return "Hello world from NoAopBusinessService.recovery";
	}

}
