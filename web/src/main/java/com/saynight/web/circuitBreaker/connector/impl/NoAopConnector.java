package com.saynight.web.circuitBreaker.connector.impl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.saynight.common.exception.BusinessException;
import com.saynight.web.circuitBreaker.connector.Connector;

import reactor.core.publisher.Flux;

@Service
public class NoAopConnector implements Connector {

    @Override
    public String failure() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, " This is a remote exception from NoAopConnector");
    }

    @Override
    public String success() {
        return " Hello World from CIRCUITBREAKERNOAOP";
    }

    @Override
    public String ignoreException() {
        throw new BusinessException(" This exception is ignored by the CircuitBreaker of CIRCUITBREAKERNOAOP");
    }

    @Override
    public Flux<String> methodWhichReturnsAStream() {
        return Flux.error(new IOException("EXAMPLE!"));
    }

}
