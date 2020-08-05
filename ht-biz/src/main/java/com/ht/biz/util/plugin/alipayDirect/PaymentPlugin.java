package com.ht.biz.util.plugin.alipayDirect;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.ht.biz.service.PaymentOrderService;
import com.ht.commons.utils.PageData;






/**
 * Plugin - 支付
 * 
 * 
 * 
 */
@SuppressWarnings("deprecation")
public abstract class PaymentPlugin implements Comparable<PaymentPlugin> {

	/**
	 * 手续费类型
	 */
	public enum FeeType {

		/** 按比例收费 */
		scale,

		/** 固定收费 */
		fixed
	}

	/**
	 * 请求方法
	 */
	public enum RequestMethod {

		/** POST */
		post,

		/** GET */
		get
	}

	/**
	 * 通知方法
	 */
	public enum NotifyMethod {

		/** 通用 */
		general,

		/** 同步 */
		sync,

		/** 异步 */
		async
	}


	
	@Autowired
	private PaymentOrderService paymentOrderService;

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public final String getId() {
		return getClass().getAnnotation(Component.class).value();
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public abstract String getName();

	/**
	 * 获取版本
	 * 
	 * @return 版本
	 */
	public abstract String getVersion();

	/**
	 * 获取作者
	 * 
	 * @return 作者
	 */
	public abstract String getAuthor();

	/**
	 * 获取网址
	 * 
	 * @return 网址
	 */
	public abstract String getSiteUrl();

	/**
	 * 获取安装URL
	 * 
	 * @return 安装URL
	 */
	public abstract String getInstallUrl();

	/**
	 * 获取un安装URL
	 * 
	 * @return un安装URL
	 */
	public abstract String getUninstallUrl();

	/**
	 * 获取设置URL
	 * 
	 * @return 设置URL
	 */
	public abstract String getSettingUrl();



	/**
	 * 获取请求URL
	 * 
	 * @return 请求URL
	 */
	public abstract String getRequestUrl();

	/**
	 * 获取请求方法
	 * 
	 * @return 请求方法
	 */
	public abstract RequestMethod getRequestMethod();

	/**
	 * 获取请求字符编码
	 * 
	 * @return 请求字符编码
	 */
	public abstract String getRequestCharset();

	/**
	 * 获取请求参数
	 * 
	 * @param sn
	 *            编号
	 * @param description
	 *            描述      
	 * @param request
	 *            httpServletRequest
	 * @return 请求参数
	 */
	public abstract Map<String, Object> getParameterMap(String sn, String description, HttpServletRequest request);

	/**
	 * 获取退款请求参数
	 * @param sn
	 * @param request
	 * @param dataMap 退款数据清单
	 * @return
	 */
	public abstract Map<String,Object> getRefundParameterMap(String sn,HttpServletRequest request, Map<String, Object> dataMap);
	
	
	/**
	 * 获取批量付款请求参数
	 * @param sn
	 * @param request
	 * @param dataMap 退款数据清单
	 * @return
	 */
	public abstract Map<String,Object> getBulkPaymentParameterMap(String sn,HttpServletRequest request, Map<String, Object> dataMap);
	
	
	
	
	/**
	 * 验证通知是否合法
	 * 
	 * @param sn
	 *            编号
	 * @param notifyMethod
	 *            通知方法
	 * @param request
	 *            httpServletRequest
	 * @return 通知是否合法
	 */
	public abstract boolean verifyNotify(String sn, NotifyMethod notifyMethod, HttpServletRequest request);
	
	
	
	
	/**
	 * 验证退款通知是否合法
	 * 
	 * @param sn
	 *            编号
	 * @param notifyMethod
	 *            通知方法
	 * @param request
	 *            httpServletRequest
	 * @return 通知是否合法
	 */
	public abstract boolean verifyRefundNotify(String sn, HttpServletRequest request);
	
	
	

	/**
	 * 获取通知返回消息
	 * 
	 * @param sn
	 *            编号
	 * @param notifyMethod
	 *            通知方法
	 * @param request
	 *            httpServletRequest
	 * @return 通知返回消息
	 */
	public abstract String getNotifyMessage(String sn, NotifyMethod notifyMethod, HttpServletRequest request);

	/**
	 * 获取超时时间
	 * 
	 * @return 超时时间
	 */
	public abstract Integer getTimeout();



	/**
	 * 计算支付金额
	 * 
	 * @param amount
	 *            金额
	 * @return 支付金额
	 */
	public BigDecimal calculateAmount(BigDecimal amount) {
		return amount.setScale(2, RoundingMode.UP);
	}

	/**
	 * 根据编号查找支付订单号
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 收款单，若不存在则返回null
	 */
	protected PageData getOrder(String sn) {
		PageData pageData=new PageData();
		pageData.put("paymentNo", sn);
		try {
			pageData=paymentOrderService.findById(pageData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageData;
	}

	/**
	 * 获取通知URL
	 * 
	 * @param sn
	 *            编号
	 * @param notifyMethod
	 *            通知方法
	 * @return 通知URL
	 */
	protected String getNotifyUrl(String sn, NotifyMethod notifyMethod, HttpServletRequest request) {
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
		/*SettingUtils utils =SettingUtils.getInstance();
	
		Properties payPro=utils.getPayProperties();*/
		if (notifyMethod == null) {
			return  basePath+"/order/notify/" + NotifyMethod.general + "/" + sn;
		}else if(notifyMethod==NotifyMethod.async){ //异步通知
			 return  basePath+"/order/asyncNotify/" + notifyMethod + "/" + sn;
		}else{ //同步通知
		    return  basePath+"/order/notify/" + notifyMethod + "/" + sn;
		}
	}



	
	
	/**
	 * 连接Map键值对
	 * 
	 * @param map
	 *            Map
	 * @param prefix
	 *            前缀
	 * @param suffix
	 *            后缀
	 * @param separator
	 *            连接符
	 * @param ignoreEmptyValue
	 *            忽略空值
	 * @param ignoreKeys
	 *            忽略Key
	 * @return 字符串
	 */
	protected String joinKeyValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
		List<String> list = new ArrayList<String>();
		if (map != null) {
			for (Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = ConvertUtils.convert(entry.getValue());
				if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
					list.add(key + "=" + (value != null ? value : ""));
				}
			}
		}
		return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
	}

	/**
	 * 连接Map值
	 * 
	 * @param map
	 *            Map
	 * @param prefix
	 *            前缀
	 * @param suffix
	 *            后缀
	 * @param separator
	 *            连接符
	 * @param ignoreEmptyValue
	 *            忽略空值
	 * @param ignoreKeys
	 *            忽略Key
	 * @return 字符串
	 */
	protected String joinValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
		List<String> list = new ArrayList<String>();
		if (map != null) {
			for (Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = ConvertUtils.convert(entry.getValue());
				if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
					list.add(value != null ? value : "");
				}
			}
		}
		return (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
	}

	/**
	 * POST请求
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	protected String post(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url);
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	
	
	/**
	 * GET请求
	 * 
	 * @param url
	 *            URL
	 * @param parameterMap
	 *            请求参数
	 * @return 返回结果
	 */
	protected String get(String url, Map<String, Object> parameterMap) {
		Assert.hasText(url);
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			if (parameterMap != null) {
				for (Entry<String, Object> entry : parameterMap.entrySet()) {
					String name = entry.getKey();
					String value = ConvertUtils.convert(entry.getValue());
					if (StringUtils.isNotEmpty(name)) {
						nameValuePairs.add(new BasicNameValuePair(name, value));
					}
				}
			}
			HttpGet httpGet = new HttpGet(url + (StringUtils.contains(url, "?") ? "&" : "?") + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")));
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			result = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PaymentPlugin other = (PaymentPlugin) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}

	public int compareTo(PaymentPlugin paymentPlugin) {
		return 0;
	}





}