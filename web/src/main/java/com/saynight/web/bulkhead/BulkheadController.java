package com.saynight.web.bulkhead;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.saynight.web.bulkhead.service.BulkheadService;

@Controller
public class BulkheadController {
	
	@Resource
	private BulkheadService bulkheadAServiceImpl;
	
	@RequestMapping("bulkhead/a/success")
	@ResponseBody
	public String successA() {
		bulkheadAServiceImpl.success();
		return "aSuccess";
	}
	
	@Resource
	private BulkheadService bulkheadBServiceImpl;
	
	@RequestMapping("bulkhead/b/success")
	@ResponseBody
	public String successB() {
		bulkheadBServiceImpl.success();
		return "aSuccess";
	}
}
