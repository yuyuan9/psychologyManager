package com.ht.commons.utils;

import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import com.ht.commons.support.ip.IpBuilder;
import com.ht.commons.support.ip.entity.TaoBaoIp;


public class RequestUtils {
	
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	
	public static TaoBaoIp getTaoBaoIp() {
		IpBuilder builder = new IpBuilder();
		return builder.builder();
	}
	
	

	public static String getIp() {
		
		HttpServletRequest request= getRequest();
		
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(StringUtils.equals("0:0:0:0:0:0:0:1", ip)) {
			ip="120.236.210.209";
		}
		return ip;
	}
	//分词机制
	public static String getParticiple(String text,String pattern){
		StringReader re = new StringReader(text);
		IKSegmenter ik = new IKSegmenter(re,true);
		Lexeme lex = null;
		StringBuffer sub=new StringBuffer();
		sub.append(pattern);
		try {
		    while((lex=ik.next())!=null){
		    	sub.append(lex.getLexemeText()+pattern);
		    }
		   // System.out.println("sub:"+sub.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sub.toString();
	}
	
	public static Boolean app2Website(HttpServletRequest request) {

		String userAgent = request.getHeader("user-agent");
		if (userAgent.indexOf("Android") != -1) {
			// 安卓
			return false;
		} else if (userAgent.indexOf("iPhone") != -1 || userAgent.indexOf("iPad") != -1) {
			// 苹果
			return false;
		} else {
			// 电脑
			return true;
		}
	}
}
