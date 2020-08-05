package com.ht.biz.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.commons.controller.BaseController;

import io.swagger.annotations.Api;
@RestController
@RequestMapping("mindmap")
@Api(value="MindMapController",description = "思维导图")
public class MindMapController extends BaseController{
	
	@RequestMapping(value="/mindMap/{page}")
	public ModelAndView page(@PathVariable String page) {
		return this.setViewName("mindMap/"+page);
	}
	
}
