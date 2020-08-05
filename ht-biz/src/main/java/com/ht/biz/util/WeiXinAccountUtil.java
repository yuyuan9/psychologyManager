package com.ht.biz.util;

import java.util.HashMap;

/**
 * 微信帐号工具
 * @author lenovo
 *
 */
public class WeiXinAccountUtil {
	public static final String  APPID="wx73e9b35d5f698d04";  //公众账号ID
	public static final String  MCHID="1315606501";          //商户号
	public static final String  PATNERKEY="maf6j3a23onjwmqpj98jwd8ke9xjiwn9"; //API密钥 KEY：商户支付密钥,微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	public static final String  APPSECRET="b785b7f080da0ce71048e59fc065a4f5"; //Appsecret 公众帐号secert,AppSecret是APPID对应的接口密码
	
	public static final String  WAPPACKAGE="MWEB";//h5 外部浏览器支付
	//public static final String  WAPSIGN="gaoqiyunkey";
	public static final String  WAPHEADURL="weixin://wap/pay?";
	public static final String  H5WEIXINWAPURL="%sappid=%s&noncestr=%s&package=%s&prepayid=%s&sign=%s&timestamp=%s";
	
	/*public static final String  APPID="wxe0439d8a048aa1bf";  //公众账号ID
	public static final String  MCHID="1252249201";          //商户号
	public static final String  PATNERKEY="qwertyuiop0987654321qasdfghjklpo"; //KEY：商户支付密钥
	public static final String  APPKEY=""; //公众帐号secert（仅JSAPI支付的时候需要配置）
*/	
	public static HashMap<String,String> openIdMap=new HashMap<String,String>();//用户OPENDID

	public static HashMap<String,Object> baseAccessTokenMap=new HashMap<String,Object>();//基础信息中的AccessToken，2小时过期。过期后需要重新获取
	
	public static  HashMap<String,Object> jsapiTicketMap=new HashMap<String,Object>(); //存储jsapi_ticket信息
	
	public static  HashMap<String,String> errorCodeMap=new HashMap<String,String>();
	static{
		errorCodeMap.put("-1","系统繁忙,此时请开发者稍候再试");
		errorCodeMap.put("0","请求成功");
		errorCodeMap.put("42001","access_token超时");
	}
	

}
