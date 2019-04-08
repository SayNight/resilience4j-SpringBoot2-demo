package com.saynight.web.circuitBreaker.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.saynight.web.circuitBreaker.connector.Connector;
import com.saynight.web.circuitBreaker.service.BusinessService;

import io.vavr.control.Try;

@Service
public class AopBusinessService implements BusinessService {
	
	@Resource
	private Connector aopConnector;

	@Override
	public String failure() {
		return aopConnector.failure();
	}

	@Override
	public String success() {
		return aopConnector.success();
	}

	@Override
	public String ignore() {
		return aopConnector.ignoreException();
	}

	@Override
	public Try<String> methodWithRecovery() {
		return Try.of(aopConnector::failure).recover((throwable) -> recovery());
	}

	private String recovery() {
		return "Hello world from AopBusinessService.recovery";
	}

}
