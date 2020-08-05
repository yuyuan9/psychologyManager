package com.ht.biz.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.ht.commons.constants.Const;
import com.ht.commons.utils.PageData;

import net.sf.json.JSONObject;

/**
 * 微信JSSDK工具类
 * 
 * @author lenovo
 *
 */
public class JsSdkUtil {
	public static String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder"; // 统一下单
	public static String orderquery = "https://api.mch.weixin.qq.com/pay/orderquery"; // 订单查询
	public static String notifyUrl = Const.siteUrl + "/order/weixinNotifyUrl"; // 通知地址
	public static final Logger logger = LoggerFactory.getLogger(JsSdkUtil.class);

	public static Map<String, String> picType = new HashMap<String, String>();
	public ServletContext servletContext;
	static {
		picType.put("image/jpeg", ".jpg");
		picType.put("image/png", ".png");
		picType.put("image/gif", ".gif");

	}

	public JsSdkUtil() {

	}

	/**
	 * 获取微信code
	 * 
	 * @param redirectUrl
	 * @param response
	 */
	public static void getWeiXinCode(HttpServletRequest request, HttpServletResponse response) {
		String redirectUrl = getRequestUrl(request);
		String codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeiXinAccountUtil.APPID
				+ "&redirect_uri=" + redirectUrl + "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		try {
			response.sendRedirect(codeUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取h5支付链接地址
	 */
	public static String getWapPayUrl(PageData pageData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String prepayId = "";
		try {
			logger.info("start getWapPayUrl .....");
			String spbill_create_ip = getIp(request);
			// System.out.println("spbill_create_ip="+spbill_create_ip);
			BigDecimal totalFee = (BigDecimal) pageData.get("amount"); // 金额(分)
			totalFee = totalFee.multiply(new BigDecimal(100));
			String productId = pageData.getString("id"); // id
			String orderNo = pageData.getString("orderNo"); // 订单编号
			String paymentNo = pageData.getString("paymentNo");// 支付订单编号
			String paymenTtitle = pageData.getString("paymenTtitle");// 支付标题
			//String openId = getOpenId(request,response,request.getSession());
			
			//logger.info("openId:"+openId);
			long money = totalFee.longValue();
			// System.out.println("money");
			// 生成预支付交易
			WXPrepay prePay = new WXPrepay();
			
			prePay.setAppid(WeiXinAccountUtil.APPID);
			prePay.setMch_id(WeiXinAccountUtil.MCHID);
			prePay.setBody(paymenTtitle);
			//prePay.setProduct_id(productId);
			//prePay.setPartnerKey(WeiXinAccountUtil.PATNERKEY);
			prePay.setOut_trade_no(paymentNo);
			prePay.setTotal_fee(money + "");
			prePay.setSpbill_create_ip(spbill_create_ip);
			prePay.setNotify_url(notifyUrl);
			prePay.setTrade_type(WeiXinAccountUtil.WAPPACKAGE);
			prePay.setSign_type("MD5");
			prePay.setScene_info(getScene_info(paymenTtitle));
			
			
			SortedMap<String, String> map = new TreeMap<String, String>();
			map.put("appid", WeiXinAccountUtil.APPID);
			map.put("mch_id", WeiXinAccountUtil.MCHID);
			map.put("body", paymenTtitle);
			map.put("out_trade_no", paymentNo);
			map.put("total_fee", money+"");
			map.put("spbill_create_ip", spbill_create_ip);
			map.put("notify_url", notifyUrl);
			map.put("trade_type", WeiXinAccountUtil.WAPPACKAGE);
			map.put("scene_info", getScene_info(paymenTtitle));
			map.put("nonce_str", prePay.getNonce_str());
			prePay.setSign(sign(map,WeiXinAccountUtil.PATNERKEY));
			
			//prePay.setOpenId(openId);
			// 获取返回的二维码url
			
			prepayId = prePay.submitXmlGetPrepayId();
			logger.info("prepayId：" + prepayId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String retUrl = StringUtils.EMPTY;

		if (!StringUtils.isBlank(prepayId)) {

			SortedMap<String, String> map = new TreeMap<String, String>();
			map.put("appid", WeiXinAccountUtil.APPID);
			map.put("noncestr", OrderUtil.CreateNoncestr());
			map.put("package", WeiXinAccountUtil.WAPPACKAGE);
			map.put("prepayid", prepayId);

			String sign = sign(map, WeiXinAccountUtil.PATNERKEY);

			retUrl = String.format(WeiXinAccountUtil.H5WEIXINWAPURL, WeiXinAccountUtil.APPID,
					OrderUtil.CreateNoncestr(), WeiXinAccountUtil.WAPPACKAGE, prepayId, sign, OrderUtil.GetTimestamp());
		}
		return WeiXinAccountUtil.WAPHEADURL+URLEncoder.encode(retUrl, "UTF-8");

	}

	/**
	 * 1、获取微信code 2、通过code获取openid
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 * 
	 */
	public static String getOpenId(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
			throws Exception {
		String code = request.getParameter("code");
		String openId = "";
		openId = (String) httpSession.getAttribute("moblieWxOpenId");
		if (StringUtils.isEmpty(openId)) {
			if (StringUtils.isEmpty(code)) {
				String redirectUrl = getRequestUrl(request);
				String codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeiXinAccountUtil.APPID
						+ "&redirect_uri=" + redirectUrl
						+ "&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
				try {
					logger.info("codeUrl=" + codeUrl);
					response.sendRedirect(codeUrl);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				String openIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeiXinAccountUtil.APPID
						+ "&secret=" + WeiXinAccountUtil.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
				logger.info("openIdUrl=" + openIdUrl);
				HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
				CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
				HttpPost httpPost = new HttpPost(openIdUrl);
				HttpResponse httpResponse;
				httpResponse = closeableHttpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String result = EntityUtils.toString(httpEntity, "UTF-8");
					JSONObject jsonData = JSONObject.fromObject(result);
					openId = jsonData.get("openid").toString(); // 用户openId
				}
			}

			if (StringUtils.isNotEmpty(openId)) {
				httpSession.setAttribute("moblieWxOpenId", openId);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = sdf.format(new Date());
		logger.info("d:" + d + "====openId" + openId);
		return openId;
	}

	/**
	 * 1、通过微信code获取用户openid 2、通过统一下单接口生成预支付交易 3、通过预支付交易单，并返回交易会话的二维码链接code_url
	 */
	public static String getQrCodeUrl(PageData pageData, HttpServletRequest request, HttpServletResponse response) {
		// String prepayid="";
		String code_url = "";
		try {
			String spbill_create_ip = getIp(request);
			// System.out.println("spbill_create_ip="+spbill_create_ip);
			BigDecimal totalFee = (BigDecimal) pageData.get("amount"); // 金额(分)
			totalFee = totalFee.multiply(new BigDecimal(100));
			String productId = pageData.getString("id"); // id
			String orderNo = pageData.getString("orderNo"); // 订单编号
			String paymentNo = pageData.getString("paymentNo");// 支付订单编号
			String paymenTtitle = pageData.getString("paymenTtitle");// 支付标题
			long money = totalFee.longValue();
			// System.out.println("money");
			// 生成预支付交易
			WXPrepay prePay = new WXPrepay();
			prePay.setAppid(WeiXinAccountUtil.APPID);
			prePay.setBody(paymenTtitle);
			prePay.setProduct_id(productId);
			prePay.setPartnerKey(WeiXinAccountUtil.PATNERKEY);
			prePay.setMch_id(WeiXinAccountUtil.MCHID);
			prePay.setNotify_url(notifyUrl);
			prePay.setOut_trade_no(paymentNo);
			prePay.setSpbill_create_ip(spbill_create_ip);
			prePay.setTotal_fee(money + "");
			prePay.setTrade_type("NATIVE");
			// 获取返回的二维码url
			code_url = prePay.submitXmlGetPrepayIdForNative();
			logger.info("返回的二维码url：" + code_url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code_url;
	}

	/**
	 * 1、通过微信code获取用户openid 2、通过统一下单接口生成预支付交易 3、通过预支付交易单，并返回预支付交易Id
	 */
	public static String getCodePrepayId(String code, PageData pageData, HttpServletRequest request,
			HttpServletResponse response) {
		String prepay_id = "";
		// String code_url="";
		try {
			String openIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WeiXinAccountUtil.APPID
					+ "&secret=" + WeiXinAccountUtil.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
			HttpPost httpPost = new HttpPost(openIdUrl);
			HttpResponse httpResponse;
			httpResponse = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				String result = EntityUtils.toString(httpEntity, "UTF-8");
				JSONObject jsonData = JSONObject.fromObject(result);
				String openid = jsonData.get("openid").toString(); // 用户openId
				String spbill_create_ip = getIp(request);
				long totalFee = (long) pageData.get("amount"); // 金额(分)
				String productId = pageData.getString("id"); // id
				String orderNo = pageData.getString("orderNo"); // 订单编号
				String paymentNo = pageData.getString("paymentNo");// 支付订单编号
				String paymenTtitle = pageData.getString("paymenTtitle");// 支付标题
				long money = totalFee * 100;
				// 生成预支付交易
				WXPrepay prePay = new WXPrepay();
				prePay.setAppid(WeiXinAccountUtil.APPID);
				prePay.setBody(paymenTtitle);
				prePay.setPartnerKey(WeiXinAccountUtil.PATNERKEY);
				prePay.setMch_id(WeiXinAccountUtil.MCHID);
				prePay.setNotify_url(notifyUrl);
				prePay.setOut_trade_no(paymentNo);
				prePay.setSpbill_create_ip(spbill_create_ip);
				prePay.setTotal_fee(money + "");
				prePay.setTrade_type("JSAPI");
				prePay.setOpenId(openid); // 用户的openId
				// 获取返回的二维码url
				prepay_id = prePay.submitXmlGetPrepayId();
				logger.info("返回的二维码url：" + prepay_id);
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prepay_id;
	}

	/**
	 * 获取ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 通过request获取url
	 */
	public static String getRequestUrl(HttpServletRequest request) {
		String params = "?";
		Map properties = request.getParameterMap();
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			params += name + "=" + value + "&";
		}

		params = params.substring(0, params.length() - 1);
		String url = request.getRequestURL().toString() + params;
		return url;
	}

	/**
	 * 分享时获取微信参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static void getShareJsSdkParameterMap(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Map<String, String> msgMap = new HashMap<String, String>();
		try {
			// 注意 URL 一定要动态获取，不能 hardcode
			String url = getRequestUrl(request);

			logger.info(url);
			String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ WeiXinAccountUtil.APPID + "&secret=" + WeiXinAccountUtil.APPSECRET + "";

			String accessToken = JsSdkUtil.getAccessToken(getAccessTokenUrl, false);
			logger.info(accessToken);

			String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken
					+ "&type=jsapi";

			String jsapi_ticket = JsSdkUtil.getJsapiTicket(ticketUrl, false);
			logger.info(jsapi_ticket);

			msgMap = JsSdkUtil.sign(jsapi_ticket, url);

			model.addAttribute("wx_msgMap", msgMap);
			model.addAttribute("wx_url", msgMap.get("url"));
			model.addAttribute("wx_jsapi_ticket", msgMap.get("jsapi_ticket"));
			model.addAttribute("wx_nonceStr", msgMap.get("nonceStr"));
			model.addAttribute("wx_timestamp", msgMap.get("timestamp"));
			model.addAttribute("wx_signature", msgMap.get("signature"));
			model.addAttribute("wx_appid", WeiXinAccountUtil.APPID);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 请求订单查询接口
	 * 
	 * @param map
	 * @param accessToken
	 * @return
	 */
	public static boolean reqOrderquery(PageData pd, Map<String, String> map) {
		logger.info("查询开始=》《》《》《》" + pd.toString());
		BigDecimal totalFee = (BigDecimal) pd.get("amount"); // 金额(分)
		totalFee = totalFee.multiply(new BigDecimal(100));
		long amountFee = totalFee.longValue();
		logger.info(pd.toString() + "查询开始=" + amountFee);
		WXOrderQuery orderQuery = new WXOrderQuery();
		orderQuery.setAppid(map.get("appid"));
		orderQuery.setMch_id(map.get("mch_id"));
		orderQuery.setTransaction_id(map.get("transaction_id"));
		orderQuery.setOut_trade_no(map.get("out_trade_no"));
		orderQuery.setNonce_str(map.get("nonce_str"));
		orderQuery.setPartnerKey(WeiXinAccountUtil.PATNERKEY);
		Map<String, String> orderMap = orderQuery.reqOrderquery();
		logger.info("orderMap=" + orderMap.toString() + "==>>>>" + orderMap.get("trade_state"));
		// 此处添加支付成功后，支付金额和实际订单金额是否等价，防止钓鱼
		if (orderMap.get("return_code") != null && orderMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
			if (orderMap.get("trade_state") != null && orderMap.get("trade_state").equalsIgnoreCase("SUCCESS")) {
				String total_fee = map.get("total_fee");
				if (Long.parseLong(total_fee) >= amountFee) {
					return true;
				}
			}
		}
		return false;
	}

	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	/**
	 * 获取accessToken
	 * 
	 * @param accessTokenUrl
	 * @param isFlush
	 * @return
	 */
	public static String getAccessToken(String accessTokenUrl, boolean isFlush) {
		String msg = "";
		try {
			long expiresTime = 0;
			if (WeiXinAccountUtil.baseAccessTokenMap.get("expires_in") != null) {
				expiresTime = (long) WeiXinAccountUtil.baseAccessTokenMap.get("expires_in");
			}
			logger.info("====" + WeiXinAccountUtil.baseAccessTokenMap.get("expires_in"));
			System.out.println("====" + WeiXinAccountUtil.baseAccessTokenMap.get("expires_in"));

			System.out.println(
					"System.currentTimeMillis()==" + System.currentTimeMillis() + ",,,expiresTime==" + expiresTime);
			logger.info("System.currentTimeMillis()==" + System.currentTimeMillis() + ",,,expiresTime==" + expiresTime);
			System.out.println("==" + (System.currentTimeMillis() - expiresTime > 0));
			logger.info("====" + WeiXinAccountUtil.baseAccessTokenMap.get("expires_in"));
			if (System.currentTimeMillis() > expiresTime || isFlush) { // accessToken过期或者强行刷新accessToken
				HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
				CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
				HttpPost httpPost = new HttpPost(accessTokenUrl);
				HttpResponse httpResponse;
				httpResponse = closeableHttpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();

				if (httpEntity != null) {
					String result = EntityUtils.toString(httpEntity, "UTF-8");
					ObjectMapper objectMapper = new ObjectMapper();

					JSONObject jsonData = JSONObject.fromObject(result);

					System.out.println("jsonData,,,,,,==" + jsonData);
					logger.info("jsonData,,,,,,==" + jsonData);
					try {
						BufferedWriter writer = new BufferedWriter(
								new FileWriter(new File("D:\\test\\crateInfo.txt"), true));

						writer.write("jsonData==" + jsonData);

						writer.close();

					} catch (Exception e) {

					}
					if (jsonData.get("errcode") == null) {
						String access_token = jsonData.get("access_token").toString(); // access_token
						Integer expires_in = (Integer) jsonData.get("expires_in"); // expires_in
						long ex = System.currentTimeMillis() + expires_in * 1000 - 60; // 过期时间
						WeiXinAccountUtil.baseAccessTokenMap.put("access_token", access_token);
						WeiXinAccountUtil.baseAccessTokenMap.put("expires_in", ex);
						msg = access_token;

					} else {
						System.out.println("accessToken获取失败");
					}
				}
				// 释放资源
				closeableHttpClient.close();
			} else {
				msg = (String) WeiXinAccountUtil.baseAccessTokenMap.get("access_token");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;

	}

	/**
	 * 获取jsapi_ticket
	 * 
	 * @param jsApiTicketUrl
	 * @param isFlush
	 *            是否强制刷新,fasle
	 * @return
	 */
	public static String getJsapiTicket(String jsApiTicketUrl, boolean isFlush) {
		String msg = "";
		try {
			long expiresTime = 0;
			if (WeiXinAccountUtil.jsapiTicketMap.get("expires_in") != null) {
				expiresTime = (long) WeiXinAccountUtil.jsapiTicketMap.get("expires_in");
			}
			if (System.currentTimeMillis() > expiresTime || isFlush) {
				HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
				CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
				HttpPost httpPost = new HttpPost(jsApiTicketUrl);
				HttpResponse httpResponse;
				httpResponse = closeableHttpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					String result = EntityUtils.toString(httpEntity, "UTF-8");
					JSONObject jsonData = JSONObject.fromObject(result);
					System.out.println("jssdk中的jsonData=" + jsonData);

					if (StringUtils.equalsIgnoreCase("0", jsonData.get("errcode").toString())) {
						String ticket = jsonData.get("ticket").toString(); // access_token
						Integer expires_in = (Integer) jsonData.get("expires_in"); // expires_in
						long ex = System.currentTimeMillis() + expires_in * 1000 - 60; // 过期时间
						WeiXinAccountUtil.jsapiTicketMap.put("ticket", ticket);
						WeiXinAccountUtil.jsapiTicketMap.put("expires_in", ex);
						msg = ticket;
					} else {
						System.out.println("accessToken已过期！");
					}
				}
				// 释放资源
				closeableHttpClient.close();
			} else {
				msg = (String) WeiXinAccountUtil.jsapiTicketMap.get("ticket");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;

	}

	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public static String sign(Map<String, String> map, String key) {
		// 排序
		String sort = sortParameters(map);
		// 拼接API秘钥
		sort = sort + "&key=" + key;
		// System.out.println(sort);
		// MD5加密
		String sign = MD5Util.MD5Encode(sort).toUpperCase();
		return sign;

	}

	private static String sortParameters(Map<String, String> map) {
		Set<String> keys = map.keySet();
		List<String> paramsBuf = new ArrayList<String>();
		for (String k : keys) {
			paramsBuf.add((k + "=" + getParamString(map, k)));
		}
		// 对参数排序
		Collections.sort(paramsBuf);
		String result = "";
		int count = paramsBuf.size();
		for (int i = 0; i < count; i++) {
			if (i < (count - 1)) {
				result += paramsBuf.get(i) + "&";
			} else {
				result += paramsBuf.get(i);
			}
		}
		return result;
	}

	/**
	 * 返回key的值
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	private static String getParamString(Map map, String key) {
		String buf = "";
		if (map.get(key) instanceof String[]) {
			buf = ((String[]) map.get(key))[0];
		} else {
			buf = (String) map.get(key);
		}
		return buf;
	}
	
	public static String getScene_info(String wap_name){
		StringBuffer str = new StringBuffer();
		str.append("{");
		str.append("\"h5_info\":{");
		str.append("\"type\":").append("\"Wap\"").append(",");
		str.append("\"wap_url\":").append("http://www.hights.cn").append(",");
		str.append("\"wap_name\":").append(wap_name);
		str.append("}");
		str.append("}");
		
		return str.toString();
		
	}
}
