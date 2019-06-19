package com.saynight.common.event;

public interface EventProcessor<T> {
	
	/**
	 * process all events
	 * @param event
	 */
	void processEvent(T event);
}
