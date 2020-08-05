package com.ht.biz.controller.app;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ht.commons.controller.BaseController;
import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.entity.policydig.PolicyDig;
import com.ht.utils.CommonsUtil;

import io.swagger.annotations.Api;

/**
 * 登录接口
 * @author jied
 *
 */
@Api(value="indexAppController",description = "手机端页登录接口管理")
@RestController
@RequestMapping(value="/ht-biz/app/login")
public class LoginAppController extends BaseController{
	
	@GetMapping(value="/{page}")
	public ModelAndView index(@PathVariable String page){
		return this.setViewName("/app/login/"+page);
	}

}
