package com.saynight.web.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saynight.web.circuitBreaker.service.BusinessService;


@RestController
@RequestMapping(value = "/aop")
public class BackendAOPController {
	
	@Resource
    private BusinessService aopBusinessService;

    @GetMapping("failure")
    public String failure(){
        return aopBusinessService.failure();
    }

    @GetMapping("success")
    public String success(){
        return aopBusinessService.success();
    }

    @GetMapping("ignore")
    public String ignore(){
        return aopBusinessService.ignore();
    }

    @GetMapping("recover")
    public String methodWithRecovery(){
        return aopBusinessService.methodWithRecovery().get();
    }
}
