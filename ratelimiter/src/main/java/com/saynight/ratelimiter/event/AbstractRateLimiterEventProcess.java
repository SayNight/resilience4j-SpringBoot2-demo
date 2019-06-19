package com.saynight.ratelimiter.event;

import java.util.Objects;

import io.github.resilience4j.ratelimiter.event.RateLimiterEvent;
import io.github.resilience4j.ratelimiter.event.RateLimiterOnFailureEvent;
import io.github.resilience4j.ratelimiter.event.RateLimiterOnSuccessEvent;

public abstract class AbstractRateLimiterEventProcess implements RateLimiterEventsProcessor{

	@Override
	public void processEvent(RateLimiterEvent event) {
		Objects.requireNonNull(event, "rateLimiterEvent must not be null");
		switch (event.getEventType()) {
		case SUCCESSFUL_ACQUIRE:
			processSuccessEvent((RateLimiterOnSuccessEvent) event);
			break;
		case FAILED_ACQUIRE:
			processFailEvent((RateLimiterOnFailureEvent) event);
			break;
		default:
			processEvent(event);
			break;
		}
	}

}
