package com.ht.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.json.JSONObject;

public class GaodeRegionUtil {
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	private static boolean checkIP(String ip) {
		if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) || ip.split(".").length != 4) {
			return false;
		}
		return true;
	}
	/** 
	* 得到本机的外网ip，出现异常时返回空串"" 
	* @return 
	*/ 
	public static String getPublicIP() { 
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (!checkIP(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!checkIP(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip; 
	}
	
	private static String readAll(Reader rd) throws Exception {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
	
	public static JSONObject readJsonFromUrl(String url) throws Exception{
        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json =JSONObject.fromObject(jsonText);
            return json;
        } finally {
            //关闭输入流
            is.close();
        }
    }
	public static Map<String,String> getAddrName(String ip) throws Exception{
		Map<String,String> map=new HashMap<String,String>();
		//调用高德地图api，key=c436e5d7154cefd654081cba296afecc
        try {
			JSONObject json = readJsonFromUrl("https://restapi.amap.com/v3/ip?key=c436e5d7154cefd654081cba296afecc&ip="+ip);
			//JSONObject json = readJsonFromUrl("https://restapi.amap.com/v3/ip?key=c436e5d7154cefd654081cba296afecc&ip=180.136.0.0");
			//System.out.println(json);
			String status=json.getString("status");
			String province=json.getString("province");
			String city=json.getString("city");
			if(StringUtils.equals(status, "1")){
				if(StringUtils.equals(province, "[]")){
					map.put("province", "广东省");
					map.put("city", "广州市");
				}else{
					map.put("province", province);
					map.put("city", city);
				}
			}else{
				map.put("province", "广东省");
				map.put("city", "广州市");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("province", "广东省");
			map.put("city", "广州市");
		}
        return map;
    }
	public static void main(String[] args) {
		try {
	//		Map<String,String> map=getAddrName();
//			System.out.println("province="+map.get("province"));
//			System.out.println("city="+map.get("city"));
			System.out.println(getAddrName("120.24.154.152"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
