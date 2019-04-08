package com.saynight.circuitbreaker.event.impl;

import org.springframework.stereotype.Service;

import com.saynight.circuitbreaker.event.AbstractCircuitBreakerEventProcess;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnCallNotPermittedEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnIgnoredErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnResetEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSuccessEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CircuitBreakerEventsProcessDemo extends AbstractCircuitBreakerEventProcess {

	@Override
	public void processSuccessEvent(CircuitBreakerOnSuccessEvent event) {
		log.info("CircuitBreakerEvent.onSuccessEvent[{}]", event.toString());
	}

	@Override
	public void processErrorEvent(CircuitBreakerOnErrorEvent event) {
		log.info("CircuitBreakerEvent.onErrorEvent[{}]", event.toString());
	}

	@Override
	public void processStateTransitionEvent(CircuitBreakerOnStateTransitionEvent event) {
		log.info("CircuitBreakerEvent.onStateTransitionEvent[{}]", event.toString());
	}

	@Override
	public void processResetEvent(CircuitBreakerOnResetEvent event) {
		log.info("CircuitBreakerEvent.onResetEvent[{}]", event.toString());
	}

	@Override
	public void processIgnoredErrorEvent(CircuitBreakerOnIgnoredErrorEvent event) {
		log.info("CircuitBreakerEvent.onIgnoredErrorEvent[{}]", event.toString());
	}

	@Override
	public void processCallNotPermittedEvent(CircuitBreakerOnCallNotPermittedEvent event) {
		log.info("CircuitBreakerEvent.onCallNotPermittedEvent[{}]", event.toString());
	}
}
