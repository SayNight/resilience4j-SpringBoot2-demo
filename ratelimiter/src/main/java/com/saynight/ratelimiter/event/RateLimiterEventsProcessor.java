package com.saynight.ratelimiter.event;

import com.saynight.common.event.EventProcessor;

import io.github.resilience4j.ratelimiter.event.RateLimiterEvent;
import io.github.resilience4j.ratelimiter.event.RateLimiterOnFailureEvent;
import io.github.resilience4j.ratelimiter.event.RateLimiterOnSuccessEvent;

public interface RateLimiterEventsProcessor extends EventProcessor<RateLimiterEvent> {
	
	void processSuccessEvent(RateLimiterOnSuccessEvent event); 

	void processFailEvent(RateLimiterOnFailureEvent event); 
}
