package com.saynight.web.circuitBreaker.connector;

import reactor.core.publisher.Flux;

public interface Connector {
	
    String failure();

    String success();

    String ignoreException();

    Flux<String> methodWhichReturnsAStream();
}
