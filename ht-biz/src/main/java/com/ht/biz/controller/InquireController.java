package com.ht.biz.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.commons.controller.BaseController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/ht-biz/inquire")
@Api(value="PolicyDigController",description = "政策立项查询页面")
public class InquireController extends BaseController{
	
	@RequestMapping(value="/{page}")
	public ModelAndView index(@PathVariable String page) {
		return this.setViewName("inquire/"+page);
	}
	
	@RequestMapping(value="/physical/{page}")
	public ModelAndView physical(@PathVariable String page) {
		return this.setViewName("physical/"+page);
	}
	
}
