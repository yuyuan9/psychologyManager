package com.ht.biz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.commons.controller.BaseController;
import com.ht.commons.utils.RequestUtils;

import io.swagger.annotations.Api;

/**
 * 
 * @author jied
 *
 */
@RestController
@RequestMapping
@Api(value="IndexController",description = "前台首页")
public class IndexController extends BaseController{
	
	@RequestMapping(value="/")
	public ModelAndView index() {
		if(!RequestUtils.app2Website(getRequest())) {
			return this.setViewName("redirect:/ht-biz/app/index/index");
		}else {
			return this.setViewName("index/index");
		}
	}


	@RequestMapping(value="/currency/{page}/{pagetwo}")
	public ModelAndView currencytwo(@PathVariable String page,@PathVariable String pagetwo) {
		return this.setViewName(page+"/"+pagetwo);
	}

	@RequestMapping(value="/app/{page}/{pagetwo}")
	public ModelAndView app(@PathVariable String page,@PathVariable String pagetwo) {
		return this.setViewName("app/"+page+"/"+pagetwo);
	}

	@RequestMapping(value="/currency/{page}")
	public ModelAndView currencyon(@PathVariable String page ) {
		return this.setViewName(page);
	}

	@RequestMapping(value="/index/{page}")
	public ModelAndView page(@PathVariable String page) {
		return this.setViewName("index/"+page);
	}
	@RequestMapping(value="/psychology/{page}")
	public ModelAndView psychology(@PathVariable String page) {
		return this.setViewName("psychology/"+page);
	}

}
