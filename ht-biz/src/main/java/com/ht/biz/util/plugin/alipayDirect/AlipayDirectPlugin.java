/*
 * 
 * 
 * 
 */
package com.ht.biz.util.plugin.alipayDirect;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ht.commons.constants.Const;
import com.ht.commons.support.sms.sdk.utils.DateUtil;
import com.ht.commons.utils.PageData;







/**
 * Plugin - 支付宝扫码支付(即时交易)
 * 
 * 
 * 
 */
@Component
@Transactional
public class AlipayDirectPlugin extends PaymentPlugin {
	

    
	@Override
	public String getName() {
		return "支付宝(即时交易)";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		
		return Const.siteName;
	
	}

	@Override
	public String getSiteUrl() {
	
		return "";

	}

	@Override
	public String getInstallUrl() {
		return "alipay_direct/install.jhtml";
	}

	@Override
	public String getUninstallUrl() {
		return "alipay_direct/uninstall.jhtml";
	}

	@Override
	public String getSettingUrl() {
		return "alipay_direct/setting.jhtml";
	}

	@Override
	public String getRequestUrl() {
		return "https://mapi.alipay.com/gateway.do";
	}

	@Override
	public RequestMethod getRequestMethod() {
		return RequestMethod.get;
	}

	@Override
	public String getRequestCharset() {
		return "UTF-8";
	}

	@Override
	public Map<String, Object> getParameterMap(String sn, String description, HttpServletRequest request) {	
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
	//	SettingUtils utils =SettingUtils.getInstance();
	//	Properties p=utils.getPayProperties();
    //	partner=p.getProperty("alipayPartner");
    //	siteUrl=p.getProperty("siteUrl");
    //	siteName=p.getProperty("siteName");
    //	alipayKey=p.getProperty("alipayKey");
		PageData pageData = getOrder(sn);
		BigDecimal amount=(BigDecimal)pageData.get("amount");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("service", "create_direct_pay_by_user");
		parameterMap.put("partner", Const.alipayPartner);
		parameterMap.put("_input_charset", "utf-8");
		parameterMap.put("sign_type", "MD5");
		parameterMap.put("return_url", getNotifyUrl(sn, NotifyMethod.sync,request));
		parameterMap.put("notify_url", getNotifyUrl(sn, NotifyMethod.async,request));
		parameterMap.put("out_trade_no", sn);
		parameterMap.put("subject", StringUtils.abbreviate(pageData.getString("paymenTtitle").replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5|. ]", ""), 60));
		parameterMap.put("body", StringUtils.abbreviate(pageData.getString("paymenTtitle").replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5|. ]", ""), 600));
		parameterMap.put("payment_type", "1");
		parameterMap.put("seller_id", Const.alipayPartner);
		parameterMap.put("it_b_pay", "30m");
		parameterMap.put("total_fee",amount.setScale(2));
		parameterMap.put("show_url", basePath);
		parameterMap.put("paymethod", "directPay");
		parameterMap.put("exter_invoke_ip", request.getLocalAddr());
		parameterMap.put("extra_common_param", AlipayDirectPlugin.class.getSimpleName());
		parameterMap.put("sign", generateSign(parameterMap));
		return parameterMap;
	}

	@Override
	public boolean verifyNotify(String sn, NotifyMethod notifyMethod, HttpServletRequest request){
		//SettingUtils utils =SettingUtils.getInstance();
		
		//Properties p=utils.getPayProperties();

    	/*partner=p.getProperty("alipayPartner");
    	siteUrl=p.getProperty("siteUrl");
    	siteName=p.getProperty("siteName");
    	alipayKey=p.getProperty("alipayKey");*/
		PageData pageData = getOrder(sn);
		BigDecimal amount=(BigDecimal)pageData.get("amount");
		if (generateSign(request.getParameterMap()).equals(request.getParameter("sign")) && Const.alipayPartner.equals(request.getParameter("seller_id")) && sn.equals(request.getParameter("out_trade_no")) && ("TRADE_SUCCESS".equals(request.getParameter("trade_status")) || "TRADE_FINISHED".equals(request.getParameter("trade_status")))
				&&amount.compareTo(new BigDecimal(request.getParameter("total_fee"))) == 0) {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("service", "notify_verify");
			parameterMap.put("partner", Const.alipayPartner);
			parameterMap.put("notify_id", request.getParameter("notify_id"));
			if ("true".equals(post("https://mapi.alipay.com/gateway.do", parameterMap))) {
				return true;
			}
		}
		return false;
	}


	
	@Override
	public String getNotifyMessage(String sn, NotifyMethod notifyMethod, HttpServletRequest request) {
		if (notifyMethod == NotifyMethod.async) {
			return "success";
		}
		return null;
	}

	@Override
	public Integer getTimeout() {
		return 21600;
	}

	/**
	 * 生成签名
	 * 
	 * @param parameterMap
	 *            参数
	 * @return 签名
	 */
	private String generateSign(Map<String, ?> parameterMap) {
		return DigestUtils.md5Hex(joinKeyValue(new TreeMap<String, Object>(parameterMap), null,  Const.alipayKey, "&", true, "sign_type", "sign"));
	}


   
	
	
	

	/**
	 * 
	 * 获取支付宝即时到帐批量退款请求参数
	 * @param request
	 * @param dataMap 
	 *  batch_num 单笔数据集格式为：第一笔交易退款数据集#第二笔交易退款数据集#第三笔，交易退款数据集…#第 N 笔交易退款数据集。
	 *  交易退款数据集的格式为：原付款支付宝交易号^退款总金额^退款理由。
	 * @return
	 */
	@Override
	public Map<String, Object> getRefundParameterMap(String sn,HttpServletRequest request,Map<String, Object> dataMap) {
		//SettingUtils utils =SettingUtils.getInstance();
		
		//Properties p=utils.getPayProperties();
    	/*partner=p.getProperty("alipayPartner");
    	siteUrl=p.getProperty("siteUrl");
    	siteName=p.getProperty("siteName");
    	alipayKey=p.getProperty("alipayKey");
    	alipayAccountName=p.getProperty("alipayAccountName");
    	alipayEmail=p.getProperty("alipayEmail");*/
    	String batch_num=(String) dataMap.get("batch_num"); //总笔数
    	String detail_data=(String) dataMap.get("detail_data"); //数据集
    	Date nowDate=new Date();
        String batchNo=getRefundBatchNo(nowDate);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("service", "refund_fastpay_by_platform_pwd");
		parameterMap.put("partner",Const.alipayPartner);
		parameterMap.put("_input_charset", "utf-8");
		parameterMap.put("sign_type", "MD5");
		parameterMap.put("notify_url", getRefundNotifyUrl(sn, batchNo,request));
		parameterMap.put("seller_user_id",Const.alipayPartner);
		parameterMap.put("refund_date", DateUtil.formatDate(nowDate,"yyyy-MM-dd HH:mm:ss"));
		parameterMap.put("batch_no", batchNo);
		parameterMap.put("batch_num",batch_num);
		parameterMap.put("detail_data",detail_data);
		parameterMap.put("sign", generateSign(parameterMap));
		return parameterMap;
	}

	/**
	 * 获取批量付款到支付宝通知URL
	 * 
	 * @param sn
	 *            编号
	 * @param notifyMethod
	 *            通知方法
	 * @return 通知URL
	 */
	protected String getBulkPaymentUrl(String sn,String batchNo,HttpServletRequest request) {
		//SettingUtils utils =SettingUtils.getInstance();
		//Properties payPro=utils.getPayProperties();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
		return  basePath+"/order/bulkPayment/" + batchNo + "/" + sn;
		
	}

	
	/**
	 * 获取退款通知URL
	 * 
	 * @param sn
	 *            编号
	 * @param notifyMethod
	 *            通知方法
	 * @return 通知URL
	 */
	protected String getRefundNotifyUrl(String sn,String batchNo,HttpServletRequest request) {
		//SettingUtils utils =SettingUtils.getInstance();
		//Properties payPro=utils.getPayProperties();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath(); 
		return  basePath+"/order/refundNotify/" + batchNo + "/" + sn;
		
	}
	
	
	/**
	 * 生成退款批次号
	 * @param sn
	 * @param notifyMethod
	 * @return
	 */
	protected  String getRefundBatchNo(Date date){
		String dStr=DateUtil.formatDate(date,"yyyyMMddHHmmssSS");
		Random random=new Random(); 
	    int rm=random.nextInt(1000000000);
	    String no=dStr+rm;
	    return no;
	}

	
	/**
	 * 生成批量付款到支付宝批次号
	 * @param sn
	 * @param notifyMethod
	 * @return
	 */
	protected  String getBulkPaymentNo(Date date){
		String dStr=DateUtil.formatDate(date,"yyyyMMddHHmmssSS");
		Random random=new Random(); 
	    int rm=random.nextInt(1000000000);
	    String no=dStr+rm;
	    String chars = "abcdefghijklmnopqrstuvwxyz";
		String msg="";
		int j=0;
		while(j<5){
			char s=chars.charAt((int)(Math.random() * 26));
			msg+=s;
			j++;
		}
	    return no+msg;
	}
	
	
	
	@Override
	public boolean verifyRefundNotify(String batchNo, HttpServletRequest request) {
	//	SettingUtils utils =SettingUtils.getInstance();
		//Properties p=utils.getPayProperties();
    /*	partner=p.getProperty("alipayPartner");
    	siteUrl=p.getProperty("siteUrl");
    	siteName=p.getProperty("siteName");
    	alipayKey=p.getProperty("alipayKey");
    	alipayAccountName=p.getProperty("alipayAccountName");
    	alipayEmail=p.getProperty("alipayEmail");*/
    	String resultDetails=request.getParameter("result_details");
    	String[] results=resultDetails.split("\\^");
    	String resultsMsg=results[results.length-1];
		if (generateSign(request.getParameterMap()).equals(request.getParameter("sign"))&& batchNo.equals(request.getParameter("batch_no"))&&"SUCCESS".equals(resultsMsg)){
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("service", "notify_verify");
			parameterMap.put("partner", Const.alipayPartner);
			parameterMap.put("notify_id", request.getParameter("notify_id"));
			if ("true".equals(post("https://mapi.alipay.com/gateway.do", parameterMap))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 发送退款请求
	 */
	public void  sendRefundRequest(String url,Map<String, Object> parameterMap){
		 post(url,parameterMap);
	}

	/**
	 * 获取批量付款到支付宝请求参数
	 * @param sn
	 * @param request
	 * @param dataMap 退款数据清单
	 * @return
	 */
	@Override
	public Map<String, Object> getBulkPaymentParameterMap(String sn, HttpServletRequest request,
			Map<String, Object> dataMap) {
		//SettingUtils utils =SettingUtils.getInstance();
		//Properties p=utils.getPayProperties();
    	/*partner=p.getProperty("alipayPartner");
    	siteUrl=p.getProperty("siteUrl");
    	siteName=p.getProperty("siteName");
    	alipayKey=p.getProperty("alipayKey");
    	alipayAccountName=p.getProperty("alipayAccountName");
    	alipayEmail=p.getProperty("alipayEmail");*/

    	String batch_fee=(String) dataMap.get("batch_fee");//付款总金额
    	String batch_num=(String) dataMap.get("batch_num"); //总笔数
    	
    	
    	/**
    	 * 付款的详细数据，最多支持1000 笔。
		 *	格式为：流水号 1^收款方账
		 *	号 1^收款账号姓名 1^付款金
		 *	额 1^备注说明 1|流水号 2^
		 *	收款方账号 2^收款账号姓名
		 *	2^付款金额 2^备注说明 2。
		 *	每条记录以“ |”间隔。
    	 */
    	String detail_data=(String) dataMap.get("detail_data"); //数据集
    	Date nowDate=new Date();
        String batchNo=getRefundBatchNo(nowDate);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("service", "batch_trans_notify");
		parameterMap.put("partner",Const.alipayPartner);
		parameterMap.put("_input_charset", "utf-8");
		parameterMap.put("sign_type", "MD5");
		parameterMap.put("notify_url", getBulkPaymentUrl(sn, batchNo,request));
		parameterMap.put("account_name",Const.alipayAccountName);
		parameterMap.put("detail_data",detail_data);
		parameterMap.put("batch_no", batchNo);
		parameterMap.put("batch_num", batch_num);
		parameterMap.put("batch_fee", batch_fee);
		parameterMap.put("email",Const.alipayEmail);
		parameterMap.put("pay_date", formatDate(nowDate, "yyyyMMdd"));
		parameterMap.put("sign", generateSign(parameterMap));
		return parameterMap;
		
	
		
	}

	
	/**
	 * 格式化日期
	 */
	public  String formatDate(Date date,String pattern){
		 SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
		 String dateStr=simpleDateFormat.format(date);
		 return dateStr;
	}
}