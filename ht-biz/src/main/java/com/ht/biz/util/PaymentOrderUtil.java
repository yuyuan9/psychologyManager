package com.ht.biz.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ht.entity.biz.honeymanager.PaymentOrder;


/**
 * 支付订单 辅助类
 * @author jied
 *
 */
public class PaymentOrderUtil {
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmSSS");
	/*
	 * 生成订单辅助类
	 */
	public static String getOrderNo(){
		synchronized (sdf) {
			return sdf.format(new Date());
		}
	}
	
	/*
	 * 生成支付
	 */
	public static PaymentOrder get(String businessType,String businessId,Double amount){
		PaymentOrder order = new PaymentOrder();
		order.setBusinessType(businessType);
		order.setBusinessId(businessId);
		order.setAmount(amount);
		order.setOrderNo(getOrderNo());
		return order;
	}
	
}
