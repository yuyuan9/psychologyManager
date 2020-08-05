package com.ht.commons.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ht.commons.controller.json.Respon;
import com.ht.commons.utils.MyPage;
import com.ht.commons.utils.PageData;
import com.ht.commons.utils.ReflectHelper;

import java.lang.reflect.Field;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
/**
 * @author jied
 *
 */
public class BaseController{
	
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	public Respon getRespon(){
		return new Respon();
	}
	
	public ModelAndView getView() {
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	public ModelAndView setViewName(String viewName) {
		ModelAndView mv = getView();
		mv.setViewName(viewName);
		return mv;
	}
	
	public HttpServletRequest getRequest() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = servletRequestAttributes.getRequest();
	    return request;
	}
	
	
	
	public MyPage getMyPage(PageData pd) {
		MyPage page = new MyPage();
		try {
			Field[] fields=ReflectHelper.getFieldAll(MyPage.class);
			for(Field field : fields) {
				String name=field.getName();
				if(pd.containsKey(name) && !StringUtils.isBlank(String.valueOf(pd.get(name)))) {
					if(StringUtils.equals(field.getGenericType().getTypeName(),long.class.getSimpleName())||
							StringUtils.equals(field.getGenericType().getTypeName(),Long.class.getSimpleName())) {
						ReflectHelper.setFieldValue(page, name, Long.valueOf(String.valueOf(pd.get(name))));
					}
				}
			}
			
			page.setPd((PageData)pd);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return page;
	}

	public static HashMap<String ,Object > getLoginInfo() throws IllegalAccessException {
		Object userinfo = SecurityUtils.getSubject().getPrincipal();
		if(null!=userinfo){
			// 得到类对象
			Class userCla = (Class) userinfo.getClass();
			Field[] fs = userCla.getDeclaredFields();
			HashMap<String ,Object > map =new HashMap<>(  );
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				f.setAccessible( true ); // 设置些属性是可以访问的
				String ss = f.getName();
				map.put( ss,f.get( userinfo ) );
			}
			return map;
		}
		return null;
	}
	
	//调用http接口
	public static String HttpStringPostRequest(String url, String json){
		String returnValue = "";
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();  
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			//第一步：创建HttpClient对象  
			httpClient = HttpClients.createDefault();  
			//第二步：创建httpPost对象  
			HttpPost httpPost = new HttpPost(url);
			//第三步：给httpPost设置JSON格式的参数  
			StringEntity requestEntity = new StringEntity(json,"utf-8");  
			requestEntity.setContentEncoding("UTF-8");
			httpPost.setHeader("Content-type", "application/json"); 
			httpPost.setEntity(requestEntity);
			//第四步：发送HttpPost请求，获取返回值  
	        returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return returnValue;
	}
	
	
}