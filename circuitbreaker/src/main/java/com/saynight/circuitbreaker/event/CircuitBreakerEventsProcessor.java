package com.saynight.circuitbreaker.event;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnCallNotPermittedEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnIgnoredErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnResetEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSuccessEvent;

public interface CircuitBreakerEventsProcessor extends EventProcessor<CircuitBreakerEvent>{
	
	/**
	 * process a successful call event
	 * @param event
	 */
    void processSuccessEvent(CircuitBreakerOnSuccessEvent event);
    
	/**
	 * process a recorded error event
	 * @param event
	 */
    void processErrorEvent(CircuitBreakerOnErrorEvent event);
    
	/**
	 * process a state transition event
	 * @param event
	 */
    void processStateTransitionEvent(CircuitBreakerOnStateTransitionEvent event);
    
	/**
	 * process a circuit breaker reset event
	 * @param event
	 */
    void processResetEvent(CircuitBreakerOnResetEvent event);

	/**
	 * process an ignored error event
	 * @param event
	 */
    void processIgnoredErrorEvent(CircuitBreakerOnIgnoredErrorEvent event);
    
	/**
	 * process a notPermitted call event
	 * @param event
	 */
    void processCallNotPermittedEvent(CircuitBreakerOnCallNotPermittedEvent event);

    
}
