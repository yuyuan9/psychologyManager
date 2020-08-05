package com.ht.biz.controller.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.commons.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/ht-biz/app/usercenter")
@Api(value="userCenterAppController",description = "手机端立个人中心管理")
public class UserCenterAppController extends BaseController{
	
	@ApiOperation(value="跳转到个人中心页面")
	@GetMapping(value="/{page}")
	public ModelAndView index(@PathVariable String page){
		return this.setViewName("/app/personage/"+page);
	}
	

}
