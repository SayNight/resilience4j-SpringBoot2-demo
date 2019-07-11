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
@RateLimiter(name = "backendA")
public class ARateLimterServiceImpl implements RateLimterService{
	
	@Autowired
	private EventConsumerRegistry<RateLimiterEvent> eventConsumerRegistry;
	
	@Autowired
	private RateLimiterEventsProcessor rateLimiterEventsProcessor;
	
	private ScheduledThreadPoolExecutor scheduled =  new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());

	
	@PostConstruct  //若注解打开，/actuator/ratelimiterevents看不到事件，看现象是该监控作用是看近期未被消费的事件
	public void init() {
		log.info("ARateLimterServiceImpl init start!!!!!!!!!!");

//		scheduled.scheduleAtFixedRate(() -> {
//			try {
//				CircularEventConsumer<RateLimiterEvent> ringBuffer = eventConsumerRegistry.getEventConsumer("backendA");
//				if(ringBuffer != null) {
//					List<RateLimiterEvent> bufferedEvents = (List<RateLimiterEvent>) ringBuffer.getBufferedEvents();
//					bufferedEvents.forEach(event -> rateLimiterEventsProcessor.processEvent(event));
//				}else {
//					log.warn("ratelimiter backendA no eventconsumer");
//
//				}
//			} catch (Exception e) {
//				log.error("error", e);
//			}
//		}, 1000, 60000, TimeUnit.MILLISECONDS); 
		log.info("ARateLimterServiceImpl init end!!!!!!!!!!");
	}
	
	@Override
	public void success() {
		log.info("aRateLimterServiceImpl.success is called!");
	}

	@Override
	public void failure() {
		log.info("aRateLimterServiceImpl.failure is called!");
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			log.error("aRateLimterServiceImpl.failure error", e);
		}
	}

}
