package com.saynight.web.ratelimiter.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saynight.web.ratelimiter.service.RateLimterService;

@Controller
public class RateLimiterController {
	
	@Resource
	private RateLimterService ARateLimterServiceImpl;
	
	@RequestMapping("rateLimiter/a/success")
	@ResponseBody
	public String successA() {
		ARateLimterServiceImpl.success();
		return "aSuccess";
	}
	
	@RequestMapping("rateLimiter/a/failure")
	@ResponseBody
	public String failureA() {
		ARateLimterServiceImpl.failure();
		return "aFailure";
	}
	
	@Resource
	private RateLimterService BRateLimterServiceImpl;
	
	@RequestMapping("rateLimiter/b/success")
	@ResponseBody
	public String successB() {
		BRateLimterServiceImpl.success();
		return "bSuccess";
	}
	
	@RequestMapping("rateLimiter/b/failure")
	@ResponseBody
	public String failureB() {
		BRateLimterServiceImpl.failure();
		return "bFailure";
	}

}
