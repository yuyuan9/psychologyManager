package com.ht.biz.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.commons.controller.BaseController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("help")
@Api(value="HelpController",description = "帮助中心")
public class HelpController extends BaseController{
	
	@RequestMapping(value="/help/{page}")
	public ModelAndView page(@PathVariable String page) {
		return this.setViewName("help/"+page);
	}
	
}
