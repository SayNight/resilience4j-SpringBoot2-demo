package com.saynight.ratelimiter.event.impl;

import org.springframework.stereotype.Service;

import com.saynight.ratelimiter.event.AbstractRateLimiterEventProcess;

import io.github.resilience4j.ratelimiter.event.RateLimiterOnFailureEvent;
import io.github.resilience4j.ratelimiter.event.RateLimiterOnSuccessEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RatelimiterEventsProcessDemo extends AbstractRateLimiterEventProcess{
	
	@Override
	public void processSuccessEvent(RateLimiterOnSuccessEvent event) {
		log.info("RateLimiterEvent.onSuccessEvent[{}]", event.toString());
	}

	@Override
	public void processFailEvent(RateLimiterOnFailureEvent event) {
		log.info("RateLimiterEvent.onFailureEvent[{}]", event.toString());
		
	}

}
