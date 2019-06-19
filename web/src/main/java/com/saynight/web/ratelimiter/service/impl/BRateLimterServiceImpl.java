package com.saynight.web.ratelimiter.service.impl;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saynight.ratelimiter.event.RateLimiterEventsProcessor;
import com.saynight.web.ratelimiter.service.RateLimterService;

import io.github.resilience4j.consumer.CircularEventConsumer;
import io.github.resilience4j.consumer.EventConsumerRegistry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.ratelimiter.event.RateLimiterEvent;
import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RateLimiter(name = "backendB")
public class BRateLimterServiceImpl implements RateLimterService{
	
	@Autowired
	private EventConsumerRegistry<RateLimiterEvent> eventConsumerRegistry;
	
	@Autowired
	private RateLimiterEventsProcessor rateLimiterEventsProcessor;
	
	private ScheduledThreadPoolExecutor scheduled =  new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());

	
	@PostConstruct  //若注解打开，/actuator/ratelimiterevents看不到事件，看现象是该监控作用是看近期未被消费的事件
	public void init() {
		log.info("BRateLimterServiceImpl init start!!!!!!!!!!");
		scheduled.scheduleAtFixedRate(() -> {
			CircularEventConsumer<RateLimiterEvent> ringBuffer = eventConsumerRegistry.getEventConsumer("backendB");
			List<RateLimiterEvent> bufferedEvents = (List<RateLimiterEvent>) ringBuffer.getBufferedEvents();
			bufferedEvents.forEach(event -> rateLimiterEventsProcessor.processEvent(event));
		}, 1000, 1000, TimeUnit.MILLISECONDS); 
		log.info("BRateLimterServiceImpl init end!!!!!!!!!!");
	}
	
	@Override
	public void success() {
		log.info("bRateLimterServiceImpl.success is called!");
	}

	@Override
	public void failure() {
		log.info("bRateLimterServiceImpl.failure is called!");
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			log.error("bRateLimterServiceImpl.failure error", e);
		}
	}
	
}
