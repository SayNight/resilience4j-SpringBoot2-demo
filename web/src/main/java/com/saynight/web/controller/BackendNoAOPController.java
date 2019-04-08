package com.saynight.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saynight.web.circuitBreaker.service.BusinessService;


@RestController
@RequestMapping(value = "/noAop")
public class BackendNoAOPController {
	
	@Resource
    private BusinessService noAopBusinessService;

    @GetMapping("failure")
    public String backendBFailure(){
        return noAopBusinessService.failure();
    }

    @GetMapping("success")
    public String backendBSuccess(){
        return noAopBusinessService.success();
    }

    @GetMapping("ignore")
    public String ignore(){
        return noAopBusinessService.ignore();
    }
}
