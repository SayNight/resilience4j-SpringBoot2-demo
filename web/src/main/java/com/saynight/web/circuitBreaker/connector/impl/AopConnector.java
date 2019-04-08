package com.saynight.web.circuitBreaker.connector.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.saynight.circuitbreaker.constants.CRConstants;
import com.saynight.common.exception.BusinessException;
import com.saynight.web.circuitBreaker.connector.Connector;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Flux;

@CircuitBreaker(name = CRConstants.CIRCUITBREAKERAOP)
@Service
public class AopConnector implements Connector {

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
