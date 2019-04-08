package com.saynight.circuitbreaker.event;

import java.util.Objects;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnCallNotPermittedEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnIgnoredErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnResetEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSuccessEvent;

public abstract class AbstractCircuitBreakerEventProcess implements CircuitBreakerEventsProcessor {

	public void processEvent(CircuitBreakerEvent event) {
		Objects.requireNonNull(event, "circuitBreakerEvent must not be null");
		switch (event.getEventType()) {
		case ERROR:
			processErrorEvent((CircuitBreakerOnErrorEvent) event);
			break;
		case IGNORED_ERROR:
			processIgnoredErrorEvent((CircuitBreakerOnIgnoredErrorEvent) event);
			break;
		case SUCCESS:
			processSuccessEvent((CircuitBreakerOnSuccessEvent) event);
			break;
		case STATE_TRANSITION:
			processStateTransitionEvent((CircuitBreakerOnStateTransitionEvent) event);
			break;
		case RESET:
			processResetEvent((CircuitBreakerOnResetEvent) event);
			break;
		case NOT_PERMITTED:
			processCallNotPermittedEvent((CircuitBreakerOnCallNotPermittedEvent) event);
			break;
		default:
			processEvent(event);
			break;
		}
	}
	
}
