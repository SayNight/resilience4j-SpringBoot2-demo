package com.saynight.circuitbreaker.event;

public interface EventProcessor<T> {
	
	/**
	 * process all events
	 * @param event
	 */
	void processEvent(T event);
}
