package com.ht.commons.support.geelink.http;

import java.io.IOException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.ht.commons.constants.Const;
import com.ht.commons.support.geelink.entity.GeelinkPolicyDig;
import com.ht.commons.support.geelink.jsonquery.Class2JsonQuery;

import ytx.org.apache.http.ParseException;

/**
 * geelink 请求客户端
 * @author jied
 */
public class GeeLinkHttpClient {
	
	
	public static String sendUTF8(String url,String json)throws ParseException,IOException{
		System.out.println(json);
		return send(url,json,"UTF-8");
	}
	public static String sendUTF8put(String url,String json)throws ParseException,IOException{

		return sendput(url,json,"UTF-8");
	}
	public static String sendGBK(String url,String json)throws ParseException,IOException{
		return send(url,json,"GBK");
	}
	
	 /**
     * 模拟请求
     * 
     * @param url        资源地址
     * @param map    参数列表
     * @param encoding    编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    private static String send(String url, String json,String encoding) throws ParseException, IOException{
    	String returnValue = String.format("{\"error\",\"%s\"}", Const.Code.C10110.getVal());
		CloseableHttpClient httpClient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try{
			//第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();
		 	
		 	//第二步：创建httpPost对象
	        HttpPost httpPost = new HttpPost(url);
	        
	        //第三步：给httpPost设置JSON格式的参数
	        StringEntity requestEntity = new StringEntity(json,encoding);
	        requestEntity.setContentEncoding(encoding);    	        
	        httpPost.setHeader("Content-type", "application/json");
	        httpPost.setEntity(requestEntity);
	       
	        //第四步：发送HttpPost请求，获取返回值
	        returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法
	      
		}catch(Exception e){
			 e.printStackTrace();
		}finally {
		    try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		 //第五步：处理返回值
	     return returnValue;
    }

	private static String sendput(String url, String json,String encoding) throws ParseException, IOException{
		String returnValue = String.format("{\"error\",\"%s\"}", Const.Code.C10110.getVal());
		CloseableHttpClient httpClient = HttpClients.createDefault();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try{
			//第一步：创建HttpClient对象
			httpClient = HttpClients.createDefault();

			//第二步：创建httpPost对象
			HttpPut httpPost = new HttpPut(url);

			//第三步：给httpPost设置JSON格式的参数
			StringEntity requestEntity = new StringEntity(json,encoding);
			requestEntity.setContentEncoding(encoding);
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(requestEntity);

			//第四步：发送HttpPost请求，获取返回值
			returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//第五步：处理返回值
		return returnValue;
	}

	public static void main(String[] args)throws Exception {
    	String url="http://120.79.238.238:9091/v2/api/search/";
    	
    	StringBuffer json = new StringBuffer();
    	json.append("{\"collection\": \"policyExpress\",\"queryParam\": { \"query\": \"高企\" }" +
				",\"highLightParam\": {  \"startTag\": \"<em>\",  \"endTag\": \"</em>\",  \"field\": " +
				"[  \"supportobj\" ] }, \"returnParam\": {  \"field\": [  \"_gl_dp_taxonomy_policyTypeTax\", \"_gl_dp_taxonomy_projectOneTax\",\"name\",\"title\",\"province\" ],\"rows\": 100,  \"start\": 0 } }");
    	System.out.println(json.toString());
    	Class2JsonQuery cj = new Class2JsonQuery(GeelinkPolicyDig.class);
    	
    	String retvalue=GeeLinkHttpClient.sendUTF8(url, cj.queryStr("科技"));
    	System.out.println(retvalue);
	}

}
