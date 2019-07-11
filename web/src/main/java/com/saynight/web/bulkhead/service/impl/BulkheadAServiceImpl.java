package com.saynight.web.bulkhead.service.impl;

import org.springframework.stereotype.Service;

import com.saynight.web.bulkhead.service.BulkheadService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Bulkhead(name = "backendA")
public class BulkheadAServiceImpl implements BulkheadService{
	
	@Override
	public void success() {
		log.info("BulkheadAServiceImpl::success()");
		try {
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
