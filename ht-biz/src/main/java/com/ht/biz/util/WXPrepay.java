package com.ht.biz.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 生成预支付订单号
 *
 */
public class WXPrepay {
	private static final Logger logger = LoggerFactory.getLogger(WXPrepay.class);
	
	private String appid;
	private String mch_id;
	private String nonce_str = OrderUtil.CreateNoncestr();
	private String body;
	private String out_trade_no;
	private String total_fee;
	private String spbill_create_ip;
	private String trade_type;
	private String notify_url;
	private String sign;
	private String partnerKey;
	private String product_id;
	// 预支付订单号
	private String prepay_id;
	private String openId; //openid,(jsapi)公众号支付一定要openId参数
	private String attach; //附加数据
	private String detail;//商品详情
	
	private String code_url; //二维码链接code_url
	
	private String scene_info;
	
	private String sign_type;
	/**
	 * 生成预支付订单
	 * 
	 * @return
	 */
	public String submitXmlGetPrepayId() {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(JsSdkUtil.unifiedorder);
		String xml = getPackage();
		StringEntity entity;
		try {
			entity = new StringEntity(xml, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			// post请求
			httpResponse = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				String result = EntityUtils.toString(httpEntity, "UTF-8");
			  /*  System.out.println("result=======" + result);
			    try{
				     BufferedWriter writer = new BufferedWriter(new FileWriter(new File("d:\\test\\crateInfo.txt")));

				     writer.write(result);
				     
				     writer.close();

				}catch(Exception e){

				     }*/
			    
				logger.info("result:" + result);
				// 过滤
				result = result.replaceAll("<![CDATA[|]]>", "");
				String prepay_id = Jsoup.parse(result).select("prepay_id").html();
				logger.info("result_msg:"+Jsoup.parse(result).select("return_msg").html());
				this.prepay_id = prepay_id;
				if (prepay_id != null)
					return prepay_id;
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prepay_id;
	}

	/**
	 * 请求订单查询接口
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> reqOrderquery() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(JsSdkUtil.orderquery);
		String xml = getPackage();  //生成xml格式
		StringEntity entity;
		Map<String, String> map = null;
		try {
			entity = new StringEntity(xml, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			// post请求
			httpResponse = closeableHttpClient.execute(httpPost);  //执行post请求
			// getEntity()
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				String result = EntityUtils.toString(httpEntity, "UTF-8");
				// 过滤
				result = result.replaceAll("<![CDATA[|]]>", "");
				try {
					map = XMLUtil.doXMLParse(result);   //返回数据放入map中
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	
	
	
	/**
	 * 扫描支付生成预支付订单
	 * @return
	 */
	public String submitXmlGetPrepayIdForNative() {
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(JsSdkUtil.unifiedorder);
		String xml = getNativeXml();
		StringEntity entity;
		try {
			entity = new StringEntity(xml, "utf-8");
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			// post请求
			httpResponse = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
			   String result = EntityUtils.toString(httpEntity, "UTF-8");
				logger.info("result：" + result);
				// 过滤
				result = result.replaceAll("<![CDATA[|]]>", "");
		
				System.out.println(Jsoup.parse(result));
				this.code_url=Jsoup.parse(result).select("code_url").html();
				if (code_url != null)
					return code_url;
			}
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_url;
	}
	
	/**
	 * 
	 * 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再按扫码、JSAPI、APP等不同场景生成交易串调起支付。
	 * @return
	 */
	public String getNativeXml(){
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("appid", this.appid);
		treeMap.put("mch_id", this.mch_id);
		treeMap.put("nonce_str", this.nonce_str);
		treeMap.put("body", this.body);
		treeMap.put("out_trade_no", this.out_trade_no);
		treeMap.put("total_fee", this.total_fee);
		treeMap.put("spbill_create_ip", this.spbill_create_ip);
		treeMap.put("trade_type", this.trade_type);
		treeMap.put("notify_url", this.notify_url);
		treeMap.put("product_id", this.product_id);
		StringBuilder sb = new StringBuilder();
		for (String key : treeMap.keySet()) {
			sb.append(key).append("=").append(treeMap.get(key)).append("&");
		}
		sb.append("key=" + partnerKey);
		sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
		treeMap.put("sign", sign);
		StringBuilder xml = new StringBuilder();
		xml.append("<xml>\n");
		for (Map.Entry<String, String> entry : treeMap.entrySet()){
			if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
				xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue()).append("]]></" + entry.getKey() + ">\n");
			} else {
				xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
			}
		}
		xml.append("</xml>");
		return xml.toString();
	}
	
	
	/**
	 * 生成生成预支付交易单请求参数
	 * 除被扫支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再按扫码、JSAPI、APP等不同场景生成交易串调起支付。
	 * @return
	 */
	public String getPackage() {
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		treeMap.put("appid", this.appid);
		treeMap.put("mch_id", this.mch_id);
		treeMap.put("nonce_str", this.nonce_str);
		treeMap.put("body", this.body);
		treeMap.put("out_trade_no", this.out_trade_no);
		treeMap.put("total_fee", this.total_fee);
		treeMap.put("spbill_create_ip", this.spbill_create_ip);
		treeMap.put("trade_type", this.trade_type);
		treeMap.put("notify_url", this.notify_url);
		treeMap.put("openid", this.openId);
		//treeMap.put("attach", this.attach);
		//treeMap.put("detail", this.detail);
		StringBuilder sb = new StringBuilder();
		for (String key : treeMap.keySet()) {
			sb.append(key).append("=").append(treeMap.get(key)).append("&");
		}
		sb.append("key=" + partnerKey);
		sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
		treeMap.put("sign", sign);
		StringBuilder xml = new StringBuilder();
		xml.append("<xml>\n");
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
				xml.append("<" + entry.getKey() + "><![CDATA[").append(entry.getValue()).append("]]></" + entry.getKey() + ">\n");
			} else {
				xml.append("<" + entry.getKey() + ">").append(entry.getValue()).append("</" + entry.getKey() + ">\n");
			}
		}
		xml.append("</xml>");
		System.out.println(xml.toString());
		logger.info(xml.toString());
		return xml.toString();
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}

	public String getScene_info() {
		return scene_info;
	}

	public void setScene_info(String scene_info) {
		this.scene_info = scene_info;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	
	

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	@Override
	public String toString() {
		return "WXPrepay [appid=" + appid + ", mch_id=" + mch_id + ", nonce_str=" + nonce_str + ", body=" + body
				+ ", out_trade_no=" + out_trade_no + ", total_fee=" + total_fee + ", spbill_create_ip="
				+ spbill_create_ip + ", trade_type=" + trade_type + ", notify_url=" + notify_url + ", sign=" + sign
				+ ", partnerKey=" + partnerKey + ", product_id=" + product_id + ", prepay_id=" + prepay_id + ", openId="
				+ openId + ", attach=" + attach + ", detail=" + detail + ", code_url=" + code_url + ", scene_info="
				+ scene_info + "]";
	}
	
	
	
}
