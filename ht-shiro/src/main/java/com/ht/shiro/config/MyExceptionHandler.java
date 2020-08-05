package com.ht.shiro.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.ht.commons.constants.Const.Code;

/**
 * 自定义全局异常处理类
 * @author jied
 *
 */
public class MyExceptionHandler implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			Exception ex) {
		ex.printStackTrace(); 
		ModelAndView mv = new ModelAndView();
		FastJsonJsonView view = new FastJsonJsonView();
		Map<String,Object> attrs=new HashMap<String,Object>();
		if(ex instanceof UnauthenticatedException) {
			attrs.put(Code.C_Key_Val.getKey(), Code.C10001.getKey());
			attrs.put(Code.C_Key_Val.getVal(),Code.C10001.getVal());
		}else if(ex instanceof UnauthorizedException) {
			attrs.put(Code.C_Key_Val.getKey(), Code.C10002.getKey());
			attrs.put(Code.C_Key_Val.getVal(),Code.C10002.getVal());
		}else {
			attrs.put(Code.C_Key_Val.getKey(), Code.C10003.getKey());
			attrs.put(Code.C_Key_Val.getVal(),Code.C10003.getVal());
		}
		
		view.setAttributesMap(attrs);
		mv.setView(view);
		return mv;
	}

}
