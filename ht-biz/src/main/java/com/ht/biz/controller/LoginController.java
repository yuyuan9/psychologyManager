package com.ht.biz.controller;

import com.ht.commons.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author jied
 *
 */
@RestController
@RequestMapping(value="/ht-biz/login")
@Api(value="IndexController",description = "前台首页")
public class LoginController extends BaseController{


	@ApiOperation(value="跳转到登录界面",notes="")
	@GetMapping(value="/{page}")
	public ModelAndView page(@PathVariable String page,String key) {
		ModelAndView modelAndView = new ModelAndView(  );
		modelAndView.addObject( "key",key );
		return this.setViewName("login/"+page);
	}
	

}
