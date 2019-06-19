package com.saynight.web.circuitBreaker.connector.impl;


import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.saynight.circuitbreaker.constants.CRConstants;
import com.saynight.circuitbreaker.event.CircuitBreakerEventsProcessor;
import com.saynight.common.exception.BusinessException;
import com.saynight.web.circuitBreaker.connector.Connector;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Flux;

@CircuitBreaker(name = CRConstants.CIRCUITBREAKERAOP)
@Service
public class AopConnector implements Connector {
	
	@Resource(name = CRConstants.CIRCUITBREAKERAOP)
	private io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker;
	@Autowired
	private CircuitBreakerEventsProcessor circuitBreakerEventsProcessor;
	private ScheduledThreadPoolExecutor scheduled =  new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
	
	//CircularEventConsumer 该种方式消费event需要去pull task
//	@PostConstruct  //若注解打开，/actuator/circuitbreakerevents看不到事件，看现象是该监控作用是看近期未被消费的事件
//	public void init() {
//		scheduled.scheduleAtFixedRate(() -> {
//			CircularEventConsumer<CircuitBreakerEvent> ringBuffer = new CircularEventConsumer<>(10);
//			circuitBreaker.getEventPublisher().onEvent(ringBuffer);
//			List<CircuitBreakerEvent> bufferedEvents = (List<CircuitBreakerEvent>) ringBuffer.getBufferedEvents();
//			bufferedEvents.forEach(event -> circuitBreakerEventsProcessor.processEvent(event));
//		}, 1000, 1000, TimeUnit.MILLISECONDS); 
//	}

    @Override
    public String failure() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, " This is a remote exception from AopConnector");
    }

    @Override
    public String ignoreException() {
        throw new BusinessException(" This exception is ignored by the CircuitBreaker of CIRCUITBREAKERAOP");
    }

    @Override
    public String success() {
        return " Hello World from CIRCUITBREAKERAOP";
    }

    @Override
    public Flux<String> methodWhichReturnsAStream() {
        return Flux.never();
    }

}
