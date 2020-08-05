package com.ht.commons.support.ip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ht.commons.support.ip.entity.TaoBaoIp;
import com.ht.commons.utils.RequestUtils;

import net.sf.json.JSONObject;


public class IpBuilder {

	private static String taobao_ip = "http://ip.taobao.com/service/getIpInfo.php?ip=%s";
	
	public TaoBaoIp builder() {
		try {
			String jsonstr=builderIpJsonStr();
			JSONObject jsonobj=JSONObject.fromObject(jsonstr);
			Object obj=jsonobj.get("data");
			TaoBaoIp taobaoip=(TaoBaoIp)JSONObject.toBean(JSONObject.fromObject(obj), TaoBaoIp.class);
			return taobaoip;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public String builderIpJsonStr() {
		 try {  
	            // HttpURLConnection  
	        	String urlStr=builderUrl();
	            URL url = new URL(urlStr);  
	            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();  
	            //   
	            httpConn.setConnectTimeout(3000);  
	            httpConn.setDoInput(true);  
	            httpConn.setRequestMethod("GET");  
	            //   
	            int respCode = httpConn.getResponseCode();  
	            if (respCode == 200) {  
	                String value= convertStream2Json(httpConn.getInputStream());
	                return value;
	            }  
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } 
		return StringUtils.EMPTY;
		
	}

	public String builderUrl()throws Exception {
		String ip = RequestUtils.getIp();
		ip = URLEncoder. encode(ip,"utf-8");
		return String.format(taobao_ip, ip);
	}
	
	
	private static String convertStream2Json(InputStream inputStream) {  
	    String jsonStr = "";  
	    // ByteArrayOutputStreaming  
	    ByteArrayOutputStream out = new ByteArrayOutputStream();  
	    byte[] buffer = new byte[1024];  
	    int len = 0;  
	    //   
	    try {  
	        while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {  
	            out.write(buffer, 0, len);  
	        }  
	        //   
	        jsonStr = new String(out.toByteArray());  
	    } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	    return jsonStr;  
	} 
	



 
	

    


}
