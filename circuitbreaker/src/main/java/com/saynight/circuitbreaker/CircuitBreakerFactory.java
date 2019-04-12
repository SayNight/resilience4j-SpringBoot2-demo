package com.saynight.circuitbreaker;

import java.time.Duration;
import java.util.function.Predicate;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Component
public class CircuitBreakerFactory {
	
	@Resource
	private CircuitBreakerRegistry circuitBreakerRegistry;// 在CircuitBreakerConfiguration中已定义 直接引入即可

	/**
	 * 创建默认配置熔断器
	 * 
	 * DEFAULT_MAX_FAILURE_THRESHOLD = 50; // Percentage
	 * DEFAULT_WAIT_DURATION_IN_OPEN_STATE = 60; // Seconds
	 * DEFAULT_RING_BUFFER_SIZE_IN_HALF_OPEN_STATE = 10;
	 * DEFAULT_RING_BUFFER_SIZE_IN_CLOSED_STATE = 100;
	 * 
	 * @return
	 */
	public CircuitBreaker create(String circuitBreakerName) {
		return circuitBreakerRegistry.circuitBreaker(circuitBreakerName);
	}

	/**
	 * 创建自定义配置熔断器
	 * 
	 * @param failureRateThreshold
	 *            故障率阈值百分比，超过该阈值，CircuitBreaker应该跳闸并开始short-circuiting
	 *            calls，阈值必须大于0且不大于100
	 * @param waitDurationInOpenState
	 *            CircuitBreaker在切换到半开之前应保持打开的等待时间，通俗可理解成熔断开启后，熔断由开启切换到半开的等待时间
	 * @param ringBufferSizeInHalfOpenState
	 *            当CircuitBreaker半开时，环形缓冲区的大小， 若设置10，则调用9次失败都不会触发熔断
	 * @param ringBufferSizeInClosedState
	 *            当CircuitBreaker关闭时，环形缓冲区的大小， 若设置10，则调用9次失败都不会触发熔断
	 * @param ignoreException
	 *            被忽略的异常，不计为 failure 且不增加 failure rate
	 * @return
	 */
	public CircuitBreaker create(String circuitBreakerName, float failureRateThreshold, int waitDurationInOpenState,
			int ringBufferSizeInHalfOpenState, int ringBufferSizeInClosedState,
			Class<? extends RuntimeException> ignoreException) {
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
				.failureRateThreshold(failureRateThreshold)
				.waitDurationInOpenState(Duration.ofMillis(waitDurationInOpenState))
				.ringBufferSizeInHalfOpenState(ringBufferSizeInHalfOpenState)
				.ringBufferSizeInClosedState(ringBufferSizeInClosedState).ignoreExceptions(ignoreException).build();

		return circuitBreakerRegistry.circuitBreaker(circuitBreakerName, circuitBreakerConfig);
	}

	public CircuitBreaker create(String circuitBreakerName, float failureRateThreshold, int waitDurationInOpenState,
			int ringBufferSizeInHalfOpenState, int ringBufferSizeInClosedState,
			Class<? extends RuntimeException> ignoreException, Predicate<Throwable> predicate) {
		CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
				.failureRateThreshold(failureRateThreshold)
				.waitDurationInOpenState(Duration.ofMillis(waitDurationInOpenState))
				.ringBufferSizeInHalfOpenState(ringBufferSizeInHalfOpenState)
				.ringBufferSizeInClosedState(ringBufferSizeInClosedState).ignoreExceptions(ignoreException)
				.recordFailure(predicate).build();

		return circuitBreakerRegistry.circuitBreaker(circuitBreakerName, circuitBreakerConfig);
	}
	
}
